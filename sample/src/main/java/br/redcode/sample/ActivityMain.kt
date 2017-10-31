package br.redcode.sample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import br.redcode.dataform.lib.ui.UIAgregadorPerguntas
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*

class ActivityMain : AppCompatActivity() {

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
            Log.e(App.TAG, "Preenchido corretamente: " + agregador.isPerguntasPreenchidasCorretamente())

            agregador.obterRespostas()
            Log.e(App.TAG, agregador.perguntas.toString())
        }
    }

    fun gerarListaPerguntas() {
        val reader = JSONReader(this)
        val json = reader.getStringFromJson(R.raw.perguntas)

        val gson = Gson()
        minhasPerguntas = gson.fromJson<MinhasPerguntas>(json, MinhasPerguntas::class.java)
    }

    private fun afterOnCreate() {
        agregador = UIAgregadorPerguntas(this, minhasPerguntas.perguntas)
        linearLayout.addView(agregador.getView())
    }

}
