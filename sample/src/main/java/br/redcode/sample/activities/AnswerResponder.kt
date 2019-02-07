package br.redcode.sample.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import br.redcode.dataform.lib.model.Answer
import br.redcode.dataform.lib.model.Form
import br.redcode.dataform.lib.model.Question
import br.redcode.dataform.lib.ui.UIForm
import br.redcode.sample.R
import br.redcode.sample.domain.ActivityCapturarImagem
import br.redcode.sample.utils.JSONReader
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_responder.*
import kotlinx.coroutines.*
import java.util.*
import kotlin.coroutines.CoroutineContext

class AnswerResponder : ActivityCapturarImagem(), CoroutineScope {

    private val question by lazy { intent.getParcelableExtra<Question>("question") }
    private val previewAnswer: Answer? by lazy { intent.getParcelableExtra<Answer?>("previewAnswer") }

    private lateinit var form: Form
    private lateinit var agregador: UIForm
    private val reader by lazy { JSONReader(this) }

    val job = Job()
    override val coroutineContext: CoroutineContext
        get() = io()

    fun io() = job + Dispatchers.IO
    fun main() = job + Dispatchers.Main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_responder)

        initializeListener()
        launch(coroutineContext) {
            afterOnCreate()
        }
    }

    private suspend fun afterOnCreate() = coroutineScope {
        launch(main()) {
            val asyncForm = async(io()) {
                val json = reader.getStringFromJson(R.raw.perguntas_3)

                val gson = Gson()
                gson.fromJson<Form>(json, Form::class.java)
            }

            form = asyncForm.await()

            // DIFERENÇA
            val newQuestion = form.questions.firstOrNull { q -> q.id == question.id }
            val newAnswers = arrayListOf<Answer>()
            val newQuestions = arrayListOf<Question>()
            val newSettings = form.settings.copy(
                    inputAnswersInOtherScreen = false,
                    showSymbolRequired = false
            )

            newQuestion?.let { newQuestions.add(newQuestion) }

            if (previewAnswer != null) {
                newAnswers.add(previewAnswer!!)
            }

            form = Form(
                    settings = newSettings,
                    questions = newQuestions,
                    lastUpdate = Date(),
                    idForm = form.idForm,
                    answers = newAnswers
            )

            agregador = UIForm(form, handlerCapturaImagem, null)
            val view = agregador.getViewGenerated(this@AnswerResponder)

            linearLayout.addView(view)

            agregador.fillAnswers(form.answers)

            textViewRequired.visibility = if (newQuestion?.required == true) View.VISIBLE else View.GONE
        }
    }

    private fun initializeListener() {
        button.setOnClickListener {

            launch(main()) {
                val isQuestionsFilledCorrect = agregador.isQuestionsFilledCorrect()

                when {
                    isQuestionsFilledCorrect -> {
                        agregador.refreshAnswers()
                        openAnswers()
                    }
                    else -> showErrorFillForm()
                }
            }
        }
    }

    private fun showErrorFillForm() = Toast.makeText(this, getString(R.string.existem_perguntas_nao_respondidas), Toast.LENGTH_LONG).show()

    private fun openAnswers() {
        val intent = Intent()

        launch(main()) {
            val answer = agregador.getAnswers().firstOrNull()

            if (answer != null) {
                intent.putExtra("answer", answer)
                setResult(RESULT_OK, intent)
                finish()
            }
        }
    }

}