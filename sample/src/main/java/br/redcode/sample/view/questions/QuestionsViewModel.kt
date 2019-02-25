package br.redcode.sample.view.questions

import br.redcode.dataform.lib.model.Answer
import br.redcode.dataform.lib.model.Form
import br.redcode.sample.R
import br.redcode.sample.domain.BaseViewModelWithLiveData
import br.redcode.sample.extensions.showProgressbar
import br.redcode.sample.utils.JSONReader
import com.google.gson.Gson
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.util.*

class QuestionsViewModel : BaseViewModelWithLiveData<Form>() {

    private val JSON_RAW = R.raw.questions_1

    val myAnswers = hashMapOf<Long, Answer>()

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
            val json = JSONReader().getStringFromJson(JSON_RAW)

            val gson = Gson()
            val form = gson.fromJson<Form>(json, Form::class.java)
            form.settings.idLayoutWrapper = R.layout.ui_question_wrapper_like_ios

            val formWithAnswers = form.copy(
                    answers = form.answers, // TODO LOAD FROM DATABASE
                    lastUpdate = Date()
            )

            myAnswers.clear()
            formWithAnswers.answers.forEach { answer -> myAnswers.put(answer.idQuestion, answer) }

            formWithAnswers
        }
    }

    fun updateAnswer(newAnswer: Answer) {
        myAnswers[newAnswer.idQuestion] = newAnswer
        saveProfile()
    }

    private fun saveProfile() {

    }

}