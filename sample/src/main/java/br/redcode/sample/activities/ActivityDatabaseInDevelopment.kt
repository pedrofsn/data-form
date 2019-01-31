package br.redcode.sample.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import br.redcode.dataform.lib.model.Answer
import br.redcode.dataform.lib.model.Form
import br.redcode.dataform.lib.model.Question
import br.redcode.dataform.lib.ui.UIForm
import br.redcode.sample.R
import br.redcode.sample.data.database.MyRoomDatabase
import br.redcode.sample.domain.ActivityCapturarImagem
import br.redcode.sample.extensions.toEntity
import br.redcode.sample.utils.JSONReader
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class ActivityDatabaseInDevelopment : ActivityCapturarImagem(), CoroutineScope {

    private lateinit var form: Form
    private lateinit var agregador: UIForm
    private val reader by lazy { JSONReader(this) }

    val job = Job()
    override val coroutineContext: CoroutineContext
        get() = io()

    fun io() = job + Dispatchers.IO
    fun main() = job + Dispatchers.Main

    private val myAnswers = hashMapOf<Long, Answer>()

    private val callbackQuestion = { question: Question ->

        val previewAnswer = myAnswers[question.id]

        val intent = Intent(this, AnswerResponder::class.java)

        intent.putExtra("question", question)
        if (previewAnswer != null) {
            intent.putExtra("previewAnswer", previewAnswer)
        }

        startActivityForResult(intent, 15)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initializeListener()
        launch(coroutineContext) {
            populateFormQuestiosn()
            afterOnCreate()
        }
    }

    private suspend fun populateFormQuestiosn() = coroutineScope {
        launch(io()) {
            val json = reader.getStringFromJson(R.raw.perguntas_3)

            val gson = Gson()
            form = gson.fromJson<Form>(json, Form::class.java)

            val fakeEntity = form.toEntity()

            MyRoomDatabase.getInstance().formDAO().insert(fakeEntity)

            val x = MyRoomDatabase.getInstance().formDAO().readForm(1)
            x.toString()
        }

    }

    private suspend fun afterOnCreate() = coroutineScope {
        launch(main()) {

            agregador = UIForm(form, handlerCapturaImagem, callbackQuestion)
            val view = agregador.getViewGenerated(this@ActivityDatabaseInDevelopment)

            linearLayout.addView(view)

            fillAnswers(form.answers)
        }
    }

    private suspend fun fillAnswers(answers: List<Answer>) = coroutineScope {
        launch(main()) {
            agregador.fillAnswers(answers)
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
        val intent = Intent(this, ActivityRespostas::class.java)

        launch(main()) {
            val answers = agregador.getAnswers()

            val asyncJson = async(io()) {
                val gson = Gson()
                val json = gson.toJson(answers)
                return@async json
            }

            val answersJSON = asyncJson.await()
            intent.putExtra("answersJSON", answersJSON)
            startActivity(intent)
        }
    }

    private suspend fun seedDatabase() = coroutineScope {
        //        val entityQuestion1 = EntityQuestion(
//                description = "Me descreva em 5 linhas",
//                information = Calendar.getInstance().toString(),
//                format = FORMAT_QUESTION_TEXTUAL_MULTI,
//                type = TYPE_QUESTION_TEXTUAL
//        )
//
//        val id1 = MyRoomDatabase.getInstance().questionDAO().insert(entityQuestion1)
//
//        val entityAnswer1 = EntityAnswer(
//                idQuestion = id1,
//                answer = "Esta é a linha 1\n2\n3\n4\n5"
//        )
//
//        MyRoomDatabase.getInstance().answerDAO().insert(entityAnswer1)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 15 && resultCode == RESULT_OK && data != null) {
            val answer = data.getParcelableExtra<Answer>("answer")

            myAnswers.put(answer.idQuestion, answer)

            launch(main()) {
                fillAnswers(myAnswers.values.toList())
            }
        }
    }

}