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
import br.redcode.sample.model.LoadType
import br.redcode.sample.model.repository.AnswerRepositoryImpl
import br.redcode.sample.model.repository.FormRepositoryImpl
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class FormViewModel : BaseViewModelWithLiveData<Form>() {

    private var case = 0
    private var idFormAnswers = INVALID_VALUE.toLong()
    private var idForm: Long = INVALID_VALUE.toLong()

    private val formRepository by lazy { FormRepositoryImpl() }
    private val answerRepository by lazy { AnswerRepositoryImpl() }

    val myAnswers = hashMapOf<Long, Answer>()

    fun load(@LoadType case: Int, idFormAnswers: Long, idForm: Long) {
        this.idFormAnswers = idFormAnswers
        this.idForm = idForm
        this.case = case
        load()
    }

    override fun load() = showProgressbar {
        launch(main()) {
            launch(io()) {
                val form: Form? = when (case) {
                    LoadType.JSON -> formRepository.loadFormFromJSON()
                    LoadType.FORM_WITH_ANSWERS_FROM_DATABASE -> formRepository.loadFormFromDatabase(
                        idFormAnswers
                    )
                    LoadType.FORM_FROM_DATABASE -> formRepository.loadOnlyFormFromDatabase(idForm)
                    else -> throw RuntimeException("Wrong paramenter brow!")
                }

                form?.settings?.idLayoutWrapper = R.layout.ui_question_wrapper_like_ios

                // FILL ANSWERS
                myAnswers.clear()
                form?.answers?.forEach { answer -> myAnswers.put(answer.idQuestion, answer) }

                liveData.postValue(form)
            }

            showProgressbar(false)
        }
    }

    fun updateAnswer(newAnswer: Answer) {
        myAnswers[newAnswer.idQuestion] = newAnswer

        launch(main()) {
            val form = liveData.value?.copy()

            val asyncSave = async(io()) {
                idFormAnswers = answerRepository.saveAnswer(
                    idForm = idForm,
                    idFormAnswers = idFormAnswers,
                    newAnswer = newAnswer
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