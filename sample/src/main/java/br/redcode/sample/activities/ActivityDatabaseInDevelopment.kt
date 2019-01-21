package br.redcode.sample.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import br.com.redcode.spinnable.library.model.Spinnable
import br.redcode.dataform.lib.domain.handlers.HandlerInputPopup
import br.redcode.dataform.lib.model.Form
import br.redcode.dataform.lib.ui.UIForm
import br.redcode.sample.R
import br.redcode.sample.domain.ActivityCapturarImagem
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initializeListener()
        launch(coroutineContext) {
            populateFormQuestiosn()
            afterOnCreate()
        }
    }

    private suspend fun populateFormQuestiosn() = coroutineScope() {
        val json = reader.getStringFromJson(R.raw.perguntas_3)

        val gson = Gson()
        form = gson.fromJson<Form>(json, Form::class.java)

//        val fakeEntity = form.toEntity()


    }

    private suspend fun afterOnCreate() = coroutineScope() {
        val handlerInputPopup = object : HandlerInputPopup() {

            override fun chamarPopup(idQuestion: Long, functionAdicionarItem: (idQuestion: Long, spinnable: Spinnable) -> Unit) {
                super.chamarPopup(idQuestion, functionAdicionarItem)

            }
        }

        launch(main()) {

            agregador = UIForm(form, handlerCapturaImagem, handlerInputPopup)
            val view = agregador.getViewGenerated(this@ActivityDatabaseInDevelopment)

            linearLayout.addView(view)

            agregador.fillAnswers(form.answers)
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

}