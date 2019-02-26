package br.redcode.sample.view.dynamic_form.form_answer

import androidx.databinding.ObservableField
import br.redcode.dataform.lib.model.Answer
import br.redcode.dataform.lib.model.Form
import br.redcode.dataform.lib.model.FormSettings
import br.redcode.dataform.lib.model.Question
import br.redcode.sample.domain.BaseViewModelWithLiveData
import br.redcode.sample.extensions.showProgressbar
import kotlinx.coroutines.launch
import java.util.*

class FormAnswerViewModel : BaseViewModelWithLiveData<Form>() {

    val isRequired = ObservableField<Boolean>(false)

    lateinit var question: Question
    var previewAnswer: Answer? = null

    fun load(mQuestion: Question, mAnswer: Answer?) {
        this.question = mQuestion
        this.previewAnswer = mAnswer
        load()
    }

    override fun load() = showProgressbar {
        launch(io()) {

            val newAnswers = arrayListOf<Answer>()
            val newQuestions = arrayListOf(question)
            val settings = FormSettings(
                    inputAnswersInOtherScreen = false,
                    showIndicatorInformation = false,
                    showIndicatorError = false,
                    showSymbolRequired = false,
                    editable = true
            )

            previewAnswer?.let { newAnswers.add(it) }

            val form = Form(
                    idForm = 0,
                    lastUpdate = Date(),
                    settings = settings,
                    answers = newAnswers,
                    questions = newQuestions
            )

            isRequired.set(question.required)

            liveData.postValue(form)
            launch(main()) { showProgressbar(false) }
        }
    }

}