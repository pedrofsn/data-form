package br.redcode.dataform.lib.domain

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import br.redcode.dataform.lib.R
import br.redcode.dataform.lib.interfaces.Perguntavel
import br.redcode.dataform.lib.model.ConfiguracaoFormulario
import br.redcode.dataform.lib.model.Pergunta
import br.redcode.dataform.lib.ui.Indicador
import br.redcode.dataform.lib.utils.Constantes

/**
 * Created by pedrofsn on 31/10/2017.
 */
abstract class UIPerguntaGeneric(val context: Context, val idLayout: Int, val pergunta: Pergunta, val configuracao: ConfiguracaoFormulario) : Perguntavel {

    private val inflater = LayoutInflater.from(context)
    private lateinit var textViewLabel: TextView
    private lateinit var textViewInformacao: TextView
    lateinit var indicador: Indicador

    open fun inicializar(): View {
        val view = inflater.inflate(idLayout, null)
        view.tag = "ui_pergunta_${pergunta.id}"
        initView(view)
        populateView()
        return view
    }

    open fun initView(view: View) {
        indicador = view.findViewById(R.id.indicador)
        textViewLabel = view.findViewById(R.id.textViewLabel)
        textViewInformacao = view.findViewById(R.id.textViewInformacao)
    }

    open fun populateView() {
        indicador.let {
            it.configuracao = configuracao
            it.setInformacao(getMensagemInformacao())
        }

        textViewLabel.text = pergunta.getDescricaoComObrigatoriedade()

        textViewInformacao.let {
            textViewInformacao.text = pergunta.informacao
            textViewInformacao.visibility = if (pergunta.informacao?.length ?: 0 > 0) View.VISIBLE else View.GONE
        }
    }

    override fun exibirMensagemErroPreenchimento(isPreenchidoCorretamente: Boolean) {
        indicador.let {
            if (isPreenchidoCorretamente.not()) it.setErro(getMensagemErroPreenchimento()) else it.hide()
        }
    }

    override fun getMensagemInformacao(): String {
        val mensagemDefaultLimites: String = pergunta.limite?.getMensagemDefault(context) ?: Constantes.STRING_VAZIA
        val mensagemInformacao: String = pergunta.informacao ?: Constantes.STRING_VAZIA

        return if (mensagemInformacao.isEmpty()) mensagemDefaultLimites else mensagemInformacao
    }

    override fun isObrigatoria() = pergunta.obrigatoria

}