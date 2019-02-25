package br.redcode.sample.view.questions

import android.view.View
import android.view.ViewGroup
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
        viewModel.load()
    }

    private fun updateUI(form: Form) {
        launch(main()) {
            updateUIForm(form)
        }
    }

    fun updatePassword(view: View?) = showMessage("implementar alteração de senhas")
    override fun updateAnswer(answer: Answer) = viewModel.updateAnswer(answer)
    override fun fillAnswers() = fillAnswers(viewModel.myAnswers.values.toList())
    override fun getViewGroupToHandleForm(): ViewGroup = binding.linearLayout
    override fun getContentView() = binding.cardView

}
