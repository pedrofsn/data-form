package br.redcode.dataform.lib.ui

import android.content.Context
import android.view.View
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import br.com.redcode.spinnable.library.model.Spinnable
import br.redcode.dataform.lib.R
import br.redcode.dataform.lib.adapter.AdapterItemRemovivel
import br.redcode.dataform.lib.domain.UIPerguntaGeneric
import br.redcode.dataform.lib.domain.handlers.HandlerInputPopup
import br.redcode.dataform.lib.extension.setCustomAdapter
import br.redcode.dataform.lib.interfaces.OnItemClickListener
import br.redcode.dataform.lib.interfaces.Perguntavel
import br.redcode.dataform.lib.model.ConfiguracaoFormulario
import br.redcode.dataform.lib.model.Pergunta
import br.redcode.dataform.lib.model.Resposta


/**
 * Created by pedrofsn on 31/10/2017.
 */
class UIPerguntaListaItemRemovivel(private val contextActivity: Context, pergunta: Pergunta, configuracao: ConfiguracaoFormulario, private val handlerInputPopup: HandlerInputPopup) : UIPerguntaGeneric(contextActivity, R.layout.ui_pergunta_lista_item_removivel, pergunta, configuracao), Perguntavel {

    private lateinit var recyclerView: androidx.recyclerview.widget.RecyclerView
    private lateinit var textViewAndamento: TextView
    private lateinit var linearLayoutAdicionar: LinearLayout
    private lateinit var relativeLayout: RelativeLayout

    private val adapter = AdapterItemRemovivel(object : OnItemClickListener {
        override fun onItemClickListener(position: Int) {
            removerItemDaLista(position)
        }
    }, configuracao)

    override fun initView(view: View) {
        super.initView(view)
        recyclerView = view.findViewById(R.id.recyclerView)
        textViewAndamento = view.findViewById(R.id.textViewAndamento)
        linearLayoutAdicionar = view.findViewById(R.id.linearLayoutAdicionar)
        relativeLayout = view.findViewById(R.id.relativeLayout)
    }

    override fun populateView() {
        super.populateView()
        recyclerView.tag = "ui_pergunta_${pergunta.id}_recyclerview"
        textViewAndamento.tag = "ui_pergunta_${pergunta.id}_textview"
        linearLayoutAdicionar.tag = "ui_pergunta_${pergunta.id}_linearlayout"

        recyclerView.setCustomAdapter(adapter, true)

        val functionAdicionarItem = { idPerguntaHandler: Int, spinnable: Spinnable ->
            if (configuracao.editavel && idPerguntaHandler == pergunta.id) {
                adapter.adicionar(spinnable)
                atualizarContador()
            }
        }
        linearLayoutAdicionar.setOnClickListener { handlerInputPopup.chamarPopup(pergunta.id, functionAdicionarItem) }

        pergunta.resposta?.respostas?.let {
            adapter.setLista(it)
        }

        atualizarContador()
    }

    private fun removerItemDaLista(position: Int) {
        if (configuracao.editavel) {
            adapter.remover(position)
            atualizarContador()
        }
    }

    private fun atualizarContador() {
        val tamanho = adapter.itemCount
        val maximo = pergunta.getLimiteMaximo()
        textViewAndamento.text = String.format(contextActivity.getString(R.string.x_barra_x), tamanho, maximo)

        linearLayoutAdicionar.isEnabled = tamanho != maximo
        if (isPreenchidoCorretamente()) indicador.hide()
        relativeLayout.visibility = if (configuracao.editavel) View.VISIBLE else View.GONE
    }

    override fun getResposta(): Resposta {
        val resposta = Resposta(respostas = ArrayList(adapter.getLista()))
        if (pergunta.resposta != null) resposta.tag = pergunta.resposta?.tag
        pergunta.resposta = resposta
        return resposta
    }

    override fun isPreenchidoCorretamente() = adapter.getLista().size in pergunta.getLimiteMaximo()..pergunta.getLimiteMinimo()
    override fun getMensagemErroPreenchimento() = String.format(contextActivity.getString(R.string.faltam_x_itens), (pergunta.getLimiteMaximo() - adapter.getLista().size))

}