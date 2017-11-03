package br.redcode.sample.activities

import android.os.Bundle
import android.widget.Toast
import br.redcode.dataform.lib.ui.UIAgregadorPerguntas
import br.redcode.sample.R
import br.redcode.sample.domain.BaseActivity
import br.redcode.sample.model.MinhasPerguntas
import br.redcode.sample.utils.JSONReader
import br.redcode.sample.utils.Utils
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*

class ActivityMain : BaseActivity() {

    private lateinit var agregador: UIAgregadorPerguntas
    private lateinit var minhasPerguntas: MinhasPerguntas

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        inicializarListeners()
        gerarListaPerguntas()
        afterOnCreate()
    }

    private fun inicializarListeners() {
        button.setOnClickListener {
            Utils.log("Preenchido corretamente: " + agregador.isPerguntasPreenchidasCorretamente())

            agregador.obterRespostas()
            val respostas = agregador.perguntas.toString()
            Utils.log(respostas)
            Toast.makeText(this, respostas, Toast.LENGTH_LONG).show()
        }
    }

    fun gerarListaPerguntas() {
        val reader = JSONReader(this)
        val json = reader.getStringFromJson(R.raw.perguntas)

        val gson = Gson()
        minhasPerguntas = gson.fromJson<MinhasPerguntas>(json, MinhasPerguntas::class.java)
    }

    private fun afterOnCreate() {
        agregador = UIAgregadorPerguntas(this, minhasPerguntas.perguntas, handlerCapturaImagem)
        linearLayout.addView(agregador.getView())
    }

}
