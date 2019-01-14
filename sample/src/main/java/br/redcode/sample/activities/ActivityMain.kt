package br.redcode.sample.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import br.com.concrete.canarinho.watcher.CPFCNPJTextWatcher
import br.com.redcode.spinnable.library.model.Spinnable
import br.redcode.dataform.lib.domain.handlers.HandlerInputPopup
import br.redcode.dataform.lib.model.FormQuestions
import br.redcode.dataform.lib.ui.UIForm
import br.redcode.dataform.lib.utils.Constants.PREFFIX_QUESTION
import br.redcode.dataform.lib.utils.Constants.SUFFIX_QUESTION_EDITTEXT
import br.redcode.dataform.lib.utils.Constants.SUFFIX_QUESTION_SPINNER
import br.redcode.sample.R
import br.redcode.sample.dialogs.DialogCheckin
import br.redcode.sample.domain.ActivityCapturarImagem
import br.redcode.sample.interfaces.OnPosicaoCadastrada
import br.redcode.sample.utils.JSONReader
import br.redcode.sample.utils.Utils
import br.redcode.sample.utils.Utils.CUSTOM_SAMPLE_FORMAT_QUESTION_TEXTUAL_CNPJ
import br.redcode.sample.utils.Utils.CUSTOM_SAMPLE_FORMAT_QUESTION_TEXTUAL_CPF
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*

class ActivityMain : ActivityCapturarImagem() {

    private lateinit var agregador: UIForm
    private lateinit var formQuestions: FormQuestions

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        inicializarListeners()
        gerarListaPerguntas()
        afterOnCreate()
    }

    private fun inicializarListeners() {
        button.setOnClickListener {
            if (agregador.isQuestionsFilledCorrect()) {
                agregador.getAnswers()
                val respostas = agregador.formQuestions.questions.toString()

                Utils.log(respostas)
                val intent = Intent(this, ActivityRespostas::class.java)
                intent.putExtra("formularioDePerguntas", formQuestions)
                startActivity(intent)
            } else {
                Toast.makeText(this, getString(R.string.existem_perguntas_nao_respondidas), Toast.LENGTH_LONG).show()
            }
        }
    }

    fun gerarListaPerguntas() {
        val reader = JSONReader(this)
        val json = reader.getStringFromJson(R.raw.perguntas)

        val gson = Gson()
        formQuestions = gson.fromJson<FormQuestions>(json, FormQuestions::class.java)
    }

    private fun afterOnCreate() {
        val handlerInputPopup = object : HandlerInputPopup() {

            override fun chamarPopup(idPergunta: Int, functionAdicionarItem: (idPergunta: Int, spinnable: Spinnable) -> Unit) {
                super.chamarPopup(idPergunta, functionAdicionarItem)
                abrirPopup(idPergunta, functionAdicionarItem)
            }
        }

        agregador = UIForm(this, formQuestions, handlerCapturaImagem, handlerInputPopup)
        linearLayout.addView(agregador.getView())

        // hack
        agregador.formQuestions.questions.filter { p -> p.format == CUSTOM_SAMPLE_FORMAT_QUESTION_TEXTUAL_CPF || p.format == CUSTOM_SAMPLE_FORMAT_QUESTION_TEXTUAL_CNPJ }.forEach { p ->
            linearLayout.findViewWithTag<EditText>("$PREFFIX_QUESTION${p.id}$SUFFIX_QUESTION_EDITTEXT").addTextChangedListener(CPFCNPJTextWatcher())
        }

        val spinner = linearLayout.findViewWithTag<Spinner>("${PREFFIX_QUESTION}4${SUFFIX_QUESTION_SPINNER}")
        val pergunta = linearLayout.findViewWithTag<LinearLayout>("${PREFFIX_QUESTION}5")
        showAskIfSpinnerHasPosition3AsSelected(spinner, pergunta)
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

    private fun abrirPopup(idPergunta: Int, functionAdicionarItem: (idPergunta: Int, spinnable: Spinnable) -> Unit) {
        when (idPergunta) {
            888 ->
                functionAdicionarItem.invoke(idPergunta, Spinnable("Título", "Teste"/*, auxiliar = Calendar.getInstance().toString()*/))
            else -> {
                DialogCheckin.customShow(this@ActivityMain, object : OnPosicaoCadastrada {
                    override fun onPosicaoCadastrada(latitude: String, longitude: String) {
                        val obj = Spinnable(latitude, longitude)
                        functionAdicionarItem.invoke(idPergunta, obj)
                    }
                })
            }
        }
    }

}
