package br.redcode.sample.view.dynamic_form.form_questions

import android.view.ViewGroup
import br.com.redcode.base.activities.BaseActivity
import br.com.redcode.base.extensions.receiveInt
import br.com.redcode.base.extensions.receiveLong
import br.com.redcode.base.mvvm.extensions.observer
import br.redcode.dataform.lib.model.Answer
import br.redcode.dataform.lib.model.Form
import br.redcode.dataform.lib.model.Question
import br.redcode.sample.R
import br.redcode.sample.databinding.ActivityFormBinding
import br.redcode.sample.domain.ActivityDynamicForm
import br.redcode.sample.view.dynamic_form.form_answer.FormAnswerActivity
import kotlinx.coroutines.launch

class FormActivity : ActivityDynamicForm<ActivityFormBinding, FormViewModel>() {

    override val layout = R.layout.activity_form
    override val classViewModel = FormViewModel::class.java

    companion object {
        const val LOAD_FORM_FROM_JSON = 0
        const val LOAD_FORM_WITH_ANSWERS_FROM_DATABASE = 1
        const val LOAD_ONLY_FORM_FROM_DATABASE = 2

        fun open(context: BaseActivity, case: Int, idForm: Long? = null, idFormAnswers: Long? = null) {
            context.goTo<FormActivity>(
                    "case" to case,
                    "idFormAnswers" to idFormAnswers,
                    "idForm" to idForm
            )
        }
    }

    private val idForm by receiveLong("idForm")
    private val idFormAnswers by receiveLong("idFormAnswers")
    private val case by receiveInt("case")

    private val labelSaved by lazy { getString(R.string.saved_) }

    private val observer = observer<Form> { updateUI(it) }
    override val onQuestionClicked = { question: Question ->
        val previewAnswer = viewModel.myAnswers[question.id]
        goTo<FormAnswerActivity>(
                REQUEST_CODE_ANSWER,
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
        viewModel.load(
                idFormAnswers = idFormAnswers,
                idForm = idForm,
                case = case
        )
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
