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
    lateinit var indicador: Indicador

    open fun inicializar(): View {
        val view = inflater.inflate(idLayout, null)

        initView(view)
        populateView()
        return view;
    }

    open fun initView(view: View) {
        indicador = view.findViewById<Indicador>(R.id.indicador)
        textViewLabel = view.findViewById<TextView>(R.id.textViewLabel)
    }

    open fun populateView() {
        configuracao.let { indicador.configuracao = it }
        indicador.setInformacao(getMensagemInformacao())
        textViewLabel.setText(pergunta.descricao)
    }

    override fun exibirMensagemErroPreenchimento(isPreenchidoCorretamente: Boolean) {
        if (isPreenchidoCorretamente.not()) {
            indicador.setErro(getMensagemErroPreenchimento())
        } else {
            indicador.hide()
        }
    }

    override fun getMensagemInformacao(): String {
        val mensagemDefaultLimites: String = pergunta.limite?.getMensagemDefault(context) ?: Constantes.STRING_VAZIA
        val mensagemInformacao: String = pergunta.informacao ?: Constantes.STRING_VAZIA

        return if (mensagemInformacao.isEmpty()) mensagemDefaultLimites else mensagemInformacao
    }

}