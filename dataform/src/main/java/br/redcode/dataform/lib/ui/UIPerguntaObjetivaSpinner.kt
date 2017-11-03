package br.redcode.dataform.lib.ui

import android.content.Context
import android.widget.Spinner
import android.widget.TextView
import br.redcode.dataform.lib.R
import br.redcode.dataform.lib.adapter.AdapterSpinnerSpinneable
import br.redcode.dataform.lib.domain.UIPerguntaGeneric
import br.redcode.dataform.lib.extension.getSpinnableFromSpinner
import br.redcode.dataform.lib.extension.setSpinnable
import br.redcode.dataform.lib.interfaces.Perguntavel
import br.redcode.dataform.lib.model.Alternativa
import br.redcode.dataform.lib.model.Pergunta
import br.redcode.dataform.lib.model.Resposta

/**
 * Created by pedrofsn on 31/10/2017.
 */
class UIPerguntaObjetivaSpinner(contextActivity: Context, val pergunta: Pergunta) : UIPerguntaGeneric<Pergunta>(contextActivity, R.layout.ui_pergunta_objetiva_spinner), Perguntavel {

    private lateinit var textViewLabel: TextView
    private lateinit var spinner: Spinner

    private lateinit var adapter: AdapterSpinnerSpinneable

    override fun initView() {
        textViewLabel = view.findViewById<TextView>(R.id.textViewLabel)
        spinner = view.findViewById<Spinner>(R.id.spinner)
    }

    override fun populateView() {
        textViewLabel.setText(pergunta.descricao)

        pergunta.alternativas?.let {
            val idPreSelecionado = pergunta.resposta?.alternativa?.id.toString()
            adapter = spinner.setSpinnable(it, true, idPreSelecionado)
        }
    }

    override fun getResposta(): Resposta {
        val spinnable = spinner.getSpinnableFromSpinner(adapter.getSpinnables())
        val resposta = Resposta()

        if (spinnable != null) {
            resposta.alternativa = spinnable as Alternativa
        }

        pergunta.resposta = resposta
        return resposta
    }

    override fun isPreenchidoCorretamente(): Boolean {
        return spinner.selectedItemPosition != 0
    }

    override fun exibirAlerta(mensagem: String) {

    }

}