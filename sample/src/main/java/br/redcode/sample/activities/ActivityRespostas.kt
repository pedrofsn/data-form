package br.redcode.sample.activities

import android.os.Bundle
import br.redcode.sample.R
import br.redcode.sample.domain.ActivityGeneric
import br.redcode.sample.utils.Utils
import kotlinx.android.synthetic.main.activity_respostas.*

/**
 * Created by pedrofsn on 03/11/2017.
 */
class ActivityRespostas(override var ativarBotaoVoltar: Boolean = true) : ActivityGeneric() {

    private val answersJSON by lazy { intent.getStringExtra("answersJSON") }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_respostas)

        updateAnswers()
    }

    private fun updateAnswers() {
        textView.text = answersJSON
        Utils.log(answersJSON)
    }

}