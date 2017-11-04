package br.redcode.sample.activities

import android.os.Bundle
import android.widget.Toast
import br.redcode.dataform.lib.ui.UIAgregadorPerguntas
import br.redcode.sample.R
import br.redcode.sample.domain.ActivityCapturarImagem
import br.redcode.sample.model.MinhasPerguntas
import br.redcode.sample.utils.JSONReader
import br.redcode.sample.utils.Utils
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.intentFor

class ActivityMain : ActivityCapturarImagem() {

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
            if (agregador.isPerguntasPreenchidasCorretamente()) {
                agregador.obterRespostas()
                val respostas = agregador.perguntas.toString()

                Utils.log(respostas)
                startActivity(intentFor<ActivityRespostas>("minhasPerguntas" to minhasPerguntas))
            } else {
                Toast.makeText(this, getString(R.string.existem_perguntas_nao_respondidas), Toast.LENGTH_LONG).show()
            }
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
