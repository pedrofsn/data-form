package br.redcode.dataform.lib.ui

import android.content.Context
import android.view.View
import android.widget.Spinner
import br.com.redcode.spinnable.library.extensions_functions.getSpinnableFromSpinner
import br.com.redcode.spinnable.library.extensions_functions.setSpinnable
import br.redcode.dataform.lib.R
import br.redcode.dataform.lib.domain.UIPerguntaGeneric
import br.redcode.dataform.lib.interfaces.Perguntavel
import br.redcode.dataform.lib.model.ConfiguracaoFormulario
import br.redcode.dataform.lib.model.Pergunta
import br.redcode.dataform.lib.model.Resposta

/**
 * Created by pedrofsn on 31/10/2017.
 */
class UIPerguntaObjetivaSpinner(private val contextActivity: Context, pergunta: Pergunta, configuracao: ConfiguracaoFormulario) : UIPerguntaGeneric(contextActivity, R.layout.ui_pergunta_objetiva_spinner, pergunta, configuracao), Perguntavel {

    private lateinit var spinner: Spinner

    override fun initView(view: View) {
        super.initView(view)
        spinner = view.findViewById<Spinner>(R.id.spinner)
    }

    override fun populateView() {
        super.populateView()
        spinner.tag = "ui_pergunta_${pergunta.id}_spinner"

        pergunta.alternativas?.let {
            val idPreSelecionado = pergunta.resposta?.alternativa?.id.toString()
            spinner.setSpinnable(it, true, idPreSelecionado)
        }

        spinner.isEnabled = configuracao.editavel
    }

    override fun getResposta(): Resposta {
        val spinnable = spinner.getSpinnableFromSpinner(pergunta.alternativas)
        val resposta = Resposta()

        if (spinnable != null) {
            resposta.alternativa = spinnable
            resposta.alternativa?.selected = true
        }

        if (pergunta.resposta != null) resposta.tag = pergunta.resposta?.tag
        pergunta.resposta = resposta
        return resposta
    }

    override fun isPreenchidoCorretamente() = spinner.selectedItemPosition != 0 || spinner.visibility == View.GONE
    override fun getMensagemErroPreenchimento() = contextActivity.getString(R.string.selecione_ao_menos_uma_alternativa)

}