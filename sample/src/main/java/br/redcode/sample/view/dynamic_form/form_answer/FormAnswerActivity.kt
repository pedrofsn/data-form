package br.redcode.sample.view.dynamic_form.form_answer

import android.content.Intent
import android.view.View
import android.view.ViewGroup
import br.com.redcode.base.mvvm.extensions.observer
import br.redcode.dataform.lib.model.Answer
import br.redcode.dataform.lib.model.Form
import br.redcode.dataform.lib.model.Question
import br.redcode.dataform.lib.ui.UIForm
import br.redcode.sample.R
import br.redcode.sample.databinding.ActivityFormAnswerBinding
import br.redcode.sample.domain.ActivityDynamicForm
import kotlinx.coroutines.launch

/*
    CREATED BY @PEDROFSN
*/

class FormAnswerActivity : ActivityDynamicForm<ActivityFormAnswerBinding, FormAnswerViewModel>() {

    override val classViewModel = FormAnswerViewModel::class.java
    override val layout = R.layout.activity_form_answer

    private val question by lazy { intent.getParcelableExtra<Question>("question") }
    private val previewAnswer: Answer? by lazy { intent.getParcelableExtra<Answer?>("previewAnswer") }

    private val observer = observer<Form> { updateUI(it) }
    override val onQuestionClicked: ((Question) -> Unit)? = null

    override fun setupUI() {
        super.setupUI()
        viewModel.liveData.observe(this, observer)
    }

    override fun afterOnCreate() {
        enableHomeAsUpActionBar()

        question?.let {
            viewModel.load(
                mQuestion = it,
                mAnswer = previewAnswer
            )
        }
    }

    private fun updateUI(form: Form) {
        launch(main()) {
            uiForm = UIForm(form, handlerCaptureImage, null)
            val view = uiForm.getViewGenerated(this@FormAnswerActivity)
            binding.linearLayout.addView(view)

            uiForm.fillAnswers(form.answers)
        }
    }

    fun saveAnswer(view: View?) {
        launch(main()) {
            val isQuestionsFilledCorrect = uiForm.isQuestionsFilledCorrect()

            when {
                isQuestionsFilledCorrect -> {
                    saveAndClose()
                }
                else -> finish()
            }
        }
    }

    private fun saveAndClose() {
        val intent = Intent()

        launch(main()) {
            val answer = uiForm.getAnswers().firstOrNull()

            if (answer != null) {
                intent.putExtra("answer", answer)
                setResult(RESULT_OK, intent)
                finish()
            }
        }
    }

    override fun getViewGroupToHandleForm(): ViewGroup? = null
    override fun updateAnswer(answer: Answer) = Unit
    override fun fillAnswers() = Unit
}