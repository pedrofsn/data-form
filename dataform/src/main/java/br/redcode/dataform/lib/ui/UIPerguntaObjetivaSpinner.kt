package br.redcode.dataform.lib.ui

import android.content.Context
import android.view.View
import android.widget.Spinner
import br.redcode.dataform.lib.R
import br.redcode.dataform.lib.adapter.AdapterSpinnerSpinneable
import br.redcode.dataform.lib.domain.UIPerguntaGeneric
import br.redcode.dataform.lib.extension.getSpinnableFromSpinner
import br.redcode.dataform.lib.extension.setSpinnable
import br.redcode.dataform.lib.interfaces.Perguntavel
import br.redcode.dataform.lib.model.Alternativa
import br.redcode.dataform.lib.model.ConfiguracaoFormulario
import br.redcode.dataform.lib.model.Pergunta
import br.redcode.dataform.lib.model.Resposta

/**
 * Created by pedrofsn on 31/10/2017.
 */
class UIPerguntaObjetivaSpinner(val contextActivity: Context, pergunta: Pergunta, configuracao: ConfiguracaoFormulario) : UIPerguntaGeneric(contextActivity, R.layout.ui_pergunta_objetiva_spinner, pergunta, configuracao), Perguntavel {

    private lateinit var spinner: Spinner

    private lateinit var adapter: AdapterSpinnerSpinneable

    override fun initView(view: View) {
        super.initView(view)
        spinner = view.findViewById<Spinner>(R.id.spinner)
    }

    override fun populateView() {
        super.populateView()

        pergunta.alternativas?.let {
            val idPreSelecionado = pergunta.resposta?.alternativa?.id.toString()
            adapter = spinner.setSpinnable(it, true, idPreSelecionado)
        }

        spinner.setTag("ui_pergunta_" + pergunta.id + "_spinner")
    }

    override fun getResposta(): Resposta {
        val spinnable = spinner.getSpinnableFromSpinner(adapter.getSpinnables())
        val resposta = Resposta()

        if (spinnable != null && spinnable is Alternativa) {
            resposta.alternativa = spinnable
            resposta.alternativa?.selecionado = true
        }

        pergunta.resposta = resposta
        return resposta
    }

    override fun isPreenchidoCorretamente(): Boolean {
        return spinner.selectedItemPosition != 0
    }

    override fun getMensagemErroPreenchimento(): String {
        return contextActivity.getString(R.string.selecione_ao_menos_uma_alternativa)
    }

}