package br.redcode.sample.activities

import android.os.Bundle
import br.redcode.dataform.lib.model.FormQuestions
import br.redcode.sample.R
import br.redcode.sample.domain.ActivityGeneric
import br.redcode.sample.model.toListDTO
import br.redcode.sample.utils.Utils
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_respostas.*

/**
 * Created by pedrofsn on 03/11/2017.
 */
class ActivityRespostas(override var ativarBotaoVoltar: Boolean = true) : ActivityGeneric() {

    private val formularioDePerguntas by lazy { intent.getParcelableExtra<FormQuestions>("formularioDePerguntas") }
    private val gson = Gson()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_respostas)

        updateAnswers()
    }

    private fun updateAnswers() {
        formularioDePerguntas?.let {
            val json = gson.toJson(it.toListDTO())

            textView.text = json
            Utils.log(json)
        }
    }

}