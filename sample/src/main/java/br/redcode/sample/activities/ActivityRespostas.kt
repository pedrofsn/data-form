package br.redcode.sample.activities

import android.os.Bundle
import br.redcode.sample.R
import br.redcode.sample.domain.ActivityGeneric
import br.redcode.sample.model.MinhasPerguntas
import br.redcode.sample.utils.Utils
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_respostas.*

/**
 * Created by pedrofsn on 03/11/2017.
 */
class ActivityRespostas(override var ativarBotaoVoltar: Boolean = true) : ActivityGeneric() {

    private var perguntas: MinhasPerguntas? = null
    private val gson = Gson()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_respostas)

        perguntas = intent.getSerializableExtra("minhasPerguntas") as MinhasPerguntas

        atualizarRespostas()
        button.setOnClickListener { atualizarRespostas() }
    }

    private fun atualizarRespostas() {
        perguntas?.let {
            val json = gson.toJson(it.toListDTO())

            textView.text = json
            Utils.log(json)
        }
    }

}