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
class UIPerguntaTextoInformativo(val contextActivity: Context, pergunta: Pergunta, configuracao: ConfiguracaoFormulario) : UIPerguntaGeneric(contextActivity, R.layout.ui_pergunta_texto_informativo, pergunta, configuracao), Perguntavel {

    private lateinit var textViewInformativo: TextView

    override fun initView(view: View) {
        super.initView(view)
        textViewInformativo = view.findViewById<TextView>(R.id.textViewInformativo)
    }

    override fun populateView() {
        super.populateView()
        pergunta.textoInformativo?.let { textViewInformativo.setText(it) }
    }

    override fun getResposta(): Resposta {
        return Resposta()
    }

    override fun isPreenchidoCorretamente(): Boolean {
        return true
    }

    override fun getMensagemErroPreenchimento(): String {
        return Constantes.STRING_VAZIA
    }

}