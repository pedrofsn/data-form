package br.redcode.sample.view.questions

import br.redcode.dataform.lib.model.Answer
import br.redcode.dataform.lib.model.Form
import br.redcode.sample.R
import br.redcode.sample.data.database.Mock.MOCK_ID_FORM
import br.redcode.sample.data.database.MyRoomDatabase
import br.redcode.sample.domain.BaseViewModelWithLiveData
import br.redcode.sample.extensions.showProgressbar
import br.redcode.sample.utils.JSONReader
import br.redcode.sample.utils.Utils
import br.redcode.sample.view.questions.QuestionsActivity.Companion.LOAD_FORM_FROM_DATABASE
import br.redcode.sample.view.questions.QuestionsActivity.Companion.LOAD_FORM_FROM_JSON
import com.google.gson.Gson
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class QuestionsViewModel : BaseViewModelWithLiveData<Form>() {

    private var case = 0
    val myAnswers = hashMapOf<Long, Answer>()

    fun load(case: Int) {
        this.case = case
        load()
    }

    override fun load() = showProgressbar {
        launch(main()) {
            launch(io()) {
                val asyncForm = when (case) {
                    LOAD_FORM_FROM_JSON -> loadFormFromJSON()
                    LOAD_FORM_FROM_DATABASE -> loadFormFromDatabase()
                    else -> throw RuntimeException("Wrong paramenter brow!")
                }

                val form = asyncForm?.await()
                form?.settings?.idLayoutWrapper = R.layout.ui_question_wrapper_like_ios

                Utils.log("carregado: $form")

//            val formWithAnswers = form.copy(
//                    answers = form.answers, // TODO LOAD FROM DATABASE
//                    lastUpdate = Date()
//            )

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

    private suspend fun loadFormFromDatabase() = coroutineScope { async(io()) { MyRoomDatabase.getInstance().formDAO().readForm(MOCK_ID_FORM) } }

    fun updateAnswer(newAnswer: Answer) {
        myAnswers[newAnswer.idQuestion] = newAnswer
    }

    fun save() {
        launch(main()) {
            val asyncSave = async(io()) {
                val form = liveData.value?.copy()
                if (form != null) {
                    MyRoomDatabase.getInstance().answerDAO().deleteAndSave(form.idForm, myAnswers)
                }
            }
            asyncSave.await()
            launch(main()) {
                showSimpleAlert("saved")
            }
        }
    }

}