package br.redcode.sample.activities

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.Toast
import br.redcode.dataform.lib.domain.handlers.HandlerInputPopup
import br.redcode.dataform.lib.interfaces.DuasLinhas
import br.redcode.dataform.lib.model.FormularioDePerguntas
import br.redcode.dataform.lib.ui.UIAgregadorPerguntas
import br.redcode.sample.R
import br.redcode.sample.dialogs.DialogCheckin
import br.redcode.sample.domain.ActivityCapturarImagem
import br.redcode.sample.interfaces.OnPosicaoCadastrada
import br.redcode.sample.model.CustomObject
import br.redcode.sample.utils.JSONReader
import br.redcode.sample.utils.Utils
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.intentFor

class ActivityMain : ActivityCapturarImagem() {

    private lateinit var agregador: UIAgregadorPerguntas
    private lateinit var formularioDePerguntas: FormularioDePerguntas

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        inicializarListeners()
        gerarListaPerguntas()
        afterOnCreate()
    }

    private fun inicializarListeners() {
        button.setOnClickListener {
            if (agregador.isPerguntasPreenchidasCorretamente()) {
                agregador.obterRespostas()
                val respostas = agregador.formularioDePerguntas.perguntas.toString()

                Utils.log(respostas)
                startActivity(intentFor<ActivityRespostas>("formularioDePerguntas" to formularioDePerguntas))
            } else {
                Toast.makeText(this, getString(R.string.existem_perguntas_nao_respondidas), Toast.LENGTH_LONG).show()
            }
        }
    }

    fun gerarListaPerguntas() {
        val reader = JSONReader(this)
        val json = reader.getStringFromJson(R.raw.perguntas)

        val gson = Gson()
        formularioDePerguntas = gson.fromJson<FormularioDePerguntas>(json, FormularioDePerguntas::class.java)
    }

    private fun afterOnCreate() {
        val handlerInputPopup = object : HandlerInputPopup() {

            override fun chamarPopup(idPergunta: Int, functionAdicionarItem: (idPergunta: Int, duasLinhas: DuasLinhas) -> Unit) {
                super.chamarPopup(idPergunta, functionAdicionarItem)
                abrirPopup(idPergunta, functionAdicionarItem)
            }
        }

        agregador = UIAgregadorPerguntas(this, formularioDePerguntas, handlerCapturaImagem, handlerInputPopup)
        linearLayout.addView(agregador.getView())

        // hack
        val spinner = linearLayout.findViewWithTag<Spinner>("ui_pergunta_8_spinner")
        val pergunta9 = linearLayout.findViewWithTag<LinearLayout>("ui_pergunta_9")

        if (pergunta9 != null && spinner != null) {
            pergunta9.visibility = View.GONE

            spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, id: Long) {
                    Utils.log("position : " + position)
                    Utils.log("id : " + id)

                    if (position == 3) {
                        pergunta9.visibility = View.VISIBLE
                    } else {
                        pergunta9.visibility = View.GONE
                    }
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {

                }
            }
        }
    }

    private fun abrirPopup(idPergunta: Int, functionAdicionarItem: (idPergunta: Int, duasLinhas: DuasLinhas) -> Unit) {
        when (idPergunta) {
            888 ->
                functionAdicionarItem.invoke(idPergunta, CustomObject("Título", "Teste").toDuasLinhas())
            else -> {
                DialogCheckin.customShow(this@ActivityMain, object : OnPosicaoCadastrada {
                    override fun onPosicaoCadastrada(latitude: String, longitude: String) {
                        val obj = CustomObject(latitude, longitude)
                        functionAdicionarItem.invoke(idPergunta, obj.toDuasLinhas())
                    }
                })
            }
        }
    }

}
