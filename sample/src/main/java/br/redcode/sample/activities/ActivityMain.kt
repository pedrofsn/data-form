package br.redcode.sample.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import br.com.concrete.canarinho.watcher.CPFCNPJTextWatcher
import br.com.redcode.spinnable.library.model.Spinnable
import br.redcode.dataform.lib.domain.handlers.HandlerInputPopup
import br.redcode.dataform.lib.model.Form
import br.redcode.dataform.lib.ui.UIForm
import br.redcode.dataform.lib.utils.Constants.PREFFIX_QUESTION
import br.redcode.dataform.lib.utils.Constants.SUFFIX_QUESTION_EDITTEXT
import br.redcode.dataform.lib.utils.Constants.SUFFIX_QUESTION_SPINNER
import br.redcode.sample.R
import br.redcode.sample.data.database.MyRoomDatabase
import br.redcode.sample.dialogs.DialogCheckin
import br.redcode.sample.domain.ActivityCapturarImagem
import br.redcode.sample.interfaces.OnPosicaoCadastrada
import br.redcode.sample.utils.JSONReader
import br.redcode.sample.utils.Utils
import br.redcode.sample.utils.Utils.CUSTOM_SAMPLE_FORMAT_QUESTION_TEXTUAL_CNPJ
import br.redcode.sample.utils.Utils.CUSTOM_SAMPLE_FORMAT_QUESTION_TEXTUAL_CPF
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class ActivityMain : ActivityCapturarImagem(), CoroutineScope {

    private lateinit var agregador: UIForm
    private lateinit var form: Form

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

        MyRoomDatabase.getInstance().formDAO().read(1)
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

    private suspend fun populateFormQuestiosn() = coroutineScope() {
        val reader = JSONReader(this@ActivityMain)
        val json = reader.getStringFromJson(R.raw.perguntas_3)

        val gson = Gson()
        form = gson.fromJson<Form>(json, Form::class.java)

    }

    private suspend fun afterOnCreate() = coroutineScope() {
        val handlerInputPopup = object : HandlerInputPopup() {

            override fun chamarPopup(idQuestion: Long, functionAdicionarItem: (idQuestion: Long, spinnable: Spinnable) -> Unit) {
                super.chamarPopup(idQuestion, functionAdicionarItem)
                abrirPopup(idQuestion, functionAdicionarItem)
            }
        }

        launch(main()) {

            agregador = UIForm(form, handlerCapturaImagem, handlerInputPopup)
            val view = agregador.getViewGenerated(this@ActivityMain)

            linearLayout.addView(view)

            // hack
            agregador.form.questions.filter { p -> p.format == CUSTOM_SAMPLE_FORMAT_QUESTION_TEXTUAL_CPF || p.format == CUSTOM_SAMPLE_FORMAT_QUESTION_TEXTUAL_CNPJ }.forEach { p ->
                linearLayout.findViewWithTag<EditText>("$PREFFIX_QUESTION${p.id}$SUFFIX_QUESTION_EDITTEXT").addTextChangedListener(CPFCNPJTextWatcher())
            }

            val spinner = linearLayout.findViewWithTag<Spinner>("${PREFFIX_QUESTION}4${SUFFIX_QUESTION_SPINNER}")
            val pergunta = linearLayout.findViewWithTag<LinearLayout>("${PREFFIX_QUESTION}5")
            showAskIfSpinnerHasPosition3AsSelected(spinner, pergunta)

            agregador.fillAnswers(form.answers)
        }
    }

    private fun showAskIfSpinnerHasPosition3AsSelected(spinner: Spinner?, pergunta: LinearLayout?) {
        if (pergunta != null && spinner != null) {
            pergunta.visibility = View.GONE

            spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, id: Long) {
                    Utils.log("position : $position")
                    Utils.log("id : $id")
                    Utils.log("text : ${spinner.adapter.getItem(position)}")

                    if (position == 3) {
                        pergunta.visibility = View.VISIBLE
                    } else {
                        pergunta.visibility = View.GONE
                    }
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {

                }
            }
        }
    }

    private fun abrirPopup(idQuestion: Long, functionAdicionarItem: (idQuestion: Long, spinnable: Spinnable) -> Unit) {
        when (idQuestion) {
            888.toLong() ->
                functionAdicionarItem.invoke(idQuestion, Spinnable("Título", "Teste"/*, auxiliar = Calendar.getInstance().toString()*/))
            else -> {
                DialogCheckin.customShow(this@ActivityMain, object : OnPosicaoCadastrada {
                    override fun onPosicaoCadastrada(latitude: String, longitude: String) {
                        val obj = Spinnable(latitude, longitude)
                        functionAdicionarItem.invoke(idQuestion, obj)
                    }
                })
            }
        }
    }

}
