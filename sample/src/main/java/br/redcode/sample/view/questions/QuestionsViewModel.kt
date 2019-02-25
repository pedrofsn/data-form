package br.redcode.sample.view.questions

import br.redcode.dataform.lib.model.Answer
import br.redcode.dataform.lib.model.Form
import br.redcode.sample.R
import br.redcode.sample.domain.BaseViewModelWithLiveData
import br.redcode.sample.extensions.showProgressbar
import br.redcode.sample.utils.JSONReader
import br.redcode.sample.view.questions.QuestionsActivity.Companion.LOAD_FORM_FROM_DATABASE
import br.redcode.sample.view.questions.QuestionsActivity.Companion.LOAD_FORM_FROM_JSON
import com.google.gson.Gson
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
            val asyncForm = loadForm()
            val form = asyncForm.await()

            liveData.postValue(form)
            showProgressbar(false)
        }
    }

    private suspend fun loadForm() = coroutineScope {
        async(io()) {
            val form: Form = when (case) {
                LOAD_FORM_FROM_JSON -> loadFormFromJSON()
                LOAD_FORM_FROM_DATABASE -> loadFormFromDatabase()
                else -> throw RuntimeException("Wrong paramenter brow!")
            }

            form.settings.idLayoutWrapper = R.layout.ui_question_wrapper_like_ios

//            val formWithAnswers = form.copy(
//                    answers = form.answers, // TODO LOAD FROM DATABASE
//                    lastUpdate = Date()
//            )

            // FILL ANSWERS
            myAnswers.clear()
            form.answers.forEach { answer -> myAnswers.put(answer.idQuestion, answer) }

            form
        }
    }

    private fun loadFormFromJSON(): Form {
        val idJsonRaw = R.raw.questions_1
        val json = JSONReader().getStringFromJson(idJsonRaw)
        return Gson().fromJson<Form>(json, Form::class.java)
    }

    private fun loadFormFromDatabase(): Form {
        val idJsonRaw = R.raw.questions_1
        val json = JSONReader().getStringFromJson(idJsonRaw)
        return Gson().fromJson<Form>(json, Form::class.java)
    }

    fun updateAnswer(newAnswer: Answer) {
        myAnswers[newAnswer.idQuestion] = newAnswer
        saveProfile()
    }

    private fun saveProfile() {

    }

}