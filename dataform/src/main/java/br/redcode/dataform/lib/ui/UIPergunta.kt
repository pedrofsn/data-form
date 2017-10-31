package br.redcode.dataform.lib.ui

import android.content.Context
import android.widget.EditText
import android.widget.TextView
import br.redcode.dataform.lib.R
import br.redcode.dataform.lib.domain.CustomUI
import br.redcode.dataform.lib.interfaces.Perguntavel
import br.redcode.dataform.lib.model.Pergunta
import br.redcode.dataform.lib.model.Resposta

/**
 * Created by pedrofsn on 31/10/2017.
 */
class UIPergunta(contextActivity: Context, val pergunta: Pergunta) : CustomUI<Pergunta>(contextActivity, pergunta, R.layout.ui_pergunta), Perguntavel {

    private lateinit var textViewLabel: TextView
    private lateinit var editText: EditText

    override fun initView() {
        textViewLabel = view.findViewById<TextView>(R.id.textViewLabel)
        editText = view.findViewById<EditText>(R.id.editText)
    }

    override fun populateView() {
        textViewLabel.setText(pergunta.descricao)
    }

    override fun getResposta(): Resposta {
        return Resposta(editText.text.toString())
    }

    override fun isPreenchidoCorretamente(): Boolean {
        return editText.text.toString().isNotEmpty()
    }

}