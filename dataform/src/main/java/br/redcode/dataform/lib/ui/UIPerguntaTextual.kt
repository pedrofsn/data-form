package br.redcode.dataform.lib.ui

import android.content.Context
import android.widget.EditText
import android.widget.TextView
import br.redcode.dataform.lib.R
import br.redcode.dataform.lib.domain.UIPerguntaGeneric
import br.redcode.dataform.lib.interfaces.Perguntavel
import br.redcode.dataform.lib.model.Pergunta
import br.redcode.dataform.lib.model.Resposta

/**
 * Created by pedrofsn on 31/10/2017.
 */
class UIPerguntaTextual(contextActivity: Context, val pergunta: Pergunta) : UIPerguntaGeneric<Pergunta>(contextActivity, R.layout.ui_pergunta_textual), Perguntavel {

    private lateinit var textViewLabel: TextView
    private lateinit var editText: EditText

    override fun initView() {
        textViewLabel = view.findViewById<TextView>(R.id.textViewLabel)
        editText = view.findViewById<EditText>(R.id.editText)
    }

    override fun populateView() {
        textViewLabel.setText(pergunta.descricao)
        pergunta.resposta?.resposta?.let { editText.setText(it) }
    }

    override fun getResposta(): Resposta {
        val resposta = Resposta(editText.text.toString().trim())
        pergunta.resposta = resposta
        return resposta
    }

    override fun isPreenchidoCorretamente(): Boolean {
        return editText.text.toString().trim().isNotEmpty()
    }

    override fun exibirAlerta(mensagem: String) {
        editText.error = mensagem
    }

}