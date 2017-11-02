package br.redcode.sample

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import br.redcode.dataform.lib.domain.ActivityCapturarImagem
import br.redcode.dataform.lib.ui.UIAgregadorPerguntas
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*

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
            Log.e(br.redcode.sample.Constantes.TAG, "Preenchido corretamente: " + agregador.isPerguntasPreenchidasCorretamente())

            agregador.obterRespostas()
            val respostas = agregador.perguntas.toString()
            Log.e(br.redcode.sample.Constantes.TAG, respostas)
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
