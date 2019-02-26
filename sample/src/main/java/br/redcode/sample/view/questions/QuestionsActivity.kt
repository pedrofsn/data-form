package br.redcode.sample.view.questions

import android.view.ViewGroup
import br.com.redcode.base.extensions.receiveInt
import br.com.redcode.base.mvvm.extensions.observer
import br.redcode.dataform.lib.model.Answer
import br.redcode.dataform.lib.model.Form
import br.redcode.dataform.lib.model.Question
import br.redcode.sample.R
import br.redcode.sample.databinding.ActivityQuestionsBinding
import br.redcode.sample.domain.ActivityDynamicForm
import br.redcode.sample.view.answer.AnswerActivity
import kotlinx.coroutines.launch

class QuestionsActivity : ActivityDynamicForm<ActivityQuestionsBinding, QuestionsViewModel>() {

    override val layout = R.layout.activity_questions
    override val classViewModel = QuestionsViewModel::class.java

    companion object {
        const val LOAD_FORM_FROM_JSON = 0
        const val LOAD_FORM_FROM_DATABASE = 1
    }

    private val case by receiveInt("case")

    private val labelSaved by lazy { getString(R.string.saved_) }

    private val observer = observer<Form> { updateUI(it) }
    override val onQuestionClicked = { question: Question ->
        val previewAnswer = viewModel.myAnswers[question.id]
        goTo<AnswerActivity>(
                ActivityDynamicForm.REQUEST_CODE_ANSWER,
                "question" to question,
                "previewAnswer" to previewAnswer
        )
    }

    override fun setupUI() {
        super.setupUI()
        viewModel.liveData.observe(this, observer)
    }

    override fun afterOnCreate() {
        enableHomeAsUpActionBar()
        viewModel.load(case)
    }

    private fun updateUI(form: Form) {
        launch(main()) {
            updateUIForm(form)
        }
    }

    override fun handleAnswers(answers: List<Answer>) {
        if (answers.isNotEmpty()) {
            viewModel.save()
        } else {
            toast(getString(R.string.no_answer))
        }
    }

    override fun handleEvent(event: String, obj: Any?) {
        when (event) {
            "onUpdatedAnswer" -> if (obj != null && obj is String && obj.isNotEmpty()) onUpdatedAnswer(obj)
            else -> super.handleEvent(event, obj)
        }
    }

    private fun onUpdatedAnswer(message: String) = showMessage(String.format(labelSaved, message))

    override fun updateAnswer(answer: Answer) = viewModel.updateAnswer(answer)
    override fun fillAnswers() = fillAnswers(viewModel.myAnswers.values.toList())
    override fun getViewGroupToHandleForm(): ViewGroup = binding.linearLayout
    override fun getContentView() = binding.cardView

}
