package br.redcode.dataform.lib.ui

import android.content.Context
import android.view.View
import android.widget.TextView
import br.redcode.dataform.lib.R
import br.redcode.dataform.lib.domain.UIPerguntaGeneric
import br.redcode.dataform.lib.interfaces.Perguntavel
import br.redcode.dataform.lib.model.ConfiguracaoFormulario
import br.redcode.dataform.lib.model.Pergunta
import br.redcode.dataform.lib.model.Resposta
import br.redcode.dataform.lib.utils.Constantes

/**
 * Created by pedrofsn on 31/10/2017.
 */
class UIPerguntaTextoInformativo(contextActivity: Context, pergunta: Pergunta, configuracao: ConfiguracaoFormulario) : UIPerguntaGeneric(contextActivity, R.layout.ui_pergunta_texto_informativo, pergunta, configuracao), Perguntavel {

    private lateinit var textViewInformativo: TextView

    override fun initView(view: View) {
        super.initView(view)
        textViewInformativo = view.findViewById(R.id.textViewInformativo)
    }

    override fun populateView() {
        super.populateView()
        textViewInformativo.setTag("ui_pergunta_" + pergunta.id + "_textview")
        pergunta.textoInformativo?.let { textViewInformativo.setText(it) }
    }

    override fun getResposta(): Resposta {
        val resposta = Resposta()
        if (pergunta.resposta != null) resposta.tag = pergunta.resposta?.tag
        return resposta
    }

    override fun isPreenchidoCorretamente(): Boolean {
        return true
    }

    override fun getMensagemErroPreenchimento(): String {
        return Constantes.STRING_VAZIA
    }

}