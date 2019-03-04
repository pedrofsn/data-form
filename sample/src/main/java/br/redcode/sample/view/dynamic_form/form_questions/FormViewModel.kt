package br.redcode.sample.view.dynamic_form.form_questions

import br.com.redcode.base.extensions.extract
import br.com.redcode.base.mvvm.extensions.isValid
import br.com.redcode.base.utils.Constants.EMPTY_STRING
import br.com.redcode.base.utils.Constants.INVALID_VALUE
import br.redcode.dataform.lib.model.Answer
import br.redcode.dataform.lib.model.Form
import br.redcode.sample.R
import br.redcode.sample.data.database.MyRoomDatabase
import br.redcode.sample.domain.BaseViewModelWithLiveData
import br.redcode.sample.extensions.showProgressbar
import br.redcode.sample.utils.JSONReader
import br.redcode.sample.view.dynamic_form.form_questions.FormActivity.Companion.LOAD_FORM_FROM_JSON
import br.redcode.sample.view.dynamic_form.form_questions.FormActivity.Companion.LOAD_FORM_WITH_ANSWERS_FROM_DATABASE
import br.redcode.sample.view.dynamic_form.form_questions.FormActivity.Companion.LOAD_ONLY_FORM_FROM_DATABASE
import com.google.gson.Gson
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class FormViewModel : BaseViewModelWithLiveData<Form>() {

    private var case = 0
    private var idFormAnswers = INVALID_VALUE.toLong()
    private var idForm: Long = INVALID_VALUE.toLong()

    val myAnswers = hashMapOf<Long, Answer>()

    fun load(case: Int, idFormAnswers: Long, idForm: Long) {
        this.idFormAnswers = idFormAnswers
        this.idForm = idForm
        this.case = case
        load()
    }

    override fun load() = showProgressbar {
        launch(main()) {
            launch(io()) {
                val asyncForm = when (case) {
                    LOAD_FORM_FROM_JSON -> loadFormFromJSON()
                    LOAD_FORM_WITH_ANSWERS_FROM_DATABASE -> loadFormFromDatabase()
                    LOAD_ONLY_FORM_FROM_DATABASE -> loadOnlyFormFromDatabase()
                    else -> throw RuntimeException("Wrong paramenter brow!")
                }

                val form = asyncForm?.await()
                form?.settings?.idLayoutWrapper = R.layout.ui_question_wrapper_like_ios

                // FILL ANSWERS
                myAnswers.clear()
                form?.answers?.forEach { answer -> myAnswers.put(answer.idQuestion, answer) }

                liveData.postValue(form)
            }

            showProgressbar(false)
        }
    }

    private suspend fun loadFormFromJSON(): Deferred<Form>? = coroutineScope {
        async(io()) {
            val idJsonRaw = R.raw.questions_1
            val json = JSONReader().getStringFromJson(idJsonRaw)
            Gson().fromJson<Form>(json, Form::class.java)
        }
    }

    private suspend fun loadFormFromDatabase() = coroutineScope {
        async(io()) {
            idFormAnswers?.let { form_with_answers_id ->
                MyRoomDatabase.getInstance().formDAO().readFormWithAnswers(form_with_answers_id)
            }
        }
    }

    private suspend fun loadOnlyFormFromDatabase() = coroutineScope {
        async(io()) {
            when {
                idForm.isValid() -> MyRoomDatabase.getInstance().formDAO().readOnlyForm(idForm = idForm)
                else -> throw RuntimeException("We need form_id")
            }
        }
    }

    fun updateAnswer(newAnswer: Answer) {
        myAnswers[newAnswer.idQuestion] = newAnswer

        launch(main()) {
            val form = liveData.value?.copy()

            val asyncSave = async(io()) {
                idFormAnswers = MyRoomDatabase.getInstance().answerDAO().deleteAndSave(
                        form_id = idForm,
                        form_with_answers_id = idFormAnswers,
                        idQuestion = newAnswer.idQuestion,
                        answer = newAnswer
                )
            }

            val asyncPreviewMessage = async(io()) {
                if (form != null) {
                    val question = form.questions.firstOrNull { it.id == newAnswer.idQuestion }
                    if (question != null) {
                        return@async extract safe newAnswer.getPreviewAnswer(question)
                    }
                }

                return@async EMPTY_STRING
            }

            asyncSave.await()
            sendEventToUI("onUpdatedAnswer", asyncPreviewMessage.await())
        }
    }

    fun save() {
        launch(main()) {
            val asyncSave = async(io()) {
                val form = liveData.value?.copy()
                if (form != null) {
                    idFormAnswers = MyRoomDatabase.getInstance().answerDAO().deleteAndSave(
                            form_id = idForm,
                            form_with_answers_id = idFormAnswers,
                            answers = myAnswers
                    )
                }
            }
            asyncSave.await()
            launch(main()) {
                showSimpleAlert("saved")
            }
        }
    }

    private fun isEdit() = idFormAnswers.isValid()
    private fun isCreate() = isEdit().not()

}