package br.redcode.dataform.lib.ui

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import br.redcode.dataform.lib.R
import br.redcode.dataform.lib.adapter.AdapterItemRemovivel
import br.redcode.dataform.lib.domain.UIPerguntaGeneric
import br.redcode.dataform.lib.domain.handlers.HandlerInputPopup
import br.redcode.dataform.lib.extension.setCustomAdapter
import br.redcode.dataform.lib.interfaces.DuasLinhas
import br.redcode.dataform.lib.interfaces.OnItemClickListener
import br.redcode.dataform.lib.interfaces.Perguntavel
import br.redcode.dataform.lib.model.ConfiguracaoFormulario
import br.redcode.dataform.lib.model.Pergunta
import br.redcode.dataform.lib.model.Resposta


/**
 * Created by pedrofsn on 31/10/2017.
 */
class UIPerguntaListaItemRemovivel(val contextActivity: Context, pergunta: Pergunta, configuracao: ConfiguracaoFormulario, val handlerInputPopup: HandlerInputPopup) : UIPerguntaGeneric(contextActivity, R.layout.ui_pergunta_lista_item_removivel, pergunta, configuracao), Perguntavel {

    private lateinit var recyclerView: RecyclerView
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
        recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        textViewAndamento = view.findViewById<TextView>(R.id.textViewAndamento)
        linearLayoutAdicionar = view.findViewById<LinearLayout>(R.id.linearLayoutAdicionar)
        relativeLayout = view.findViewById<RelativeLayout>(R.id.relativeLayout)
    }

    override fun populateView() {
        super.populateView()
        recyclerView.setTag("ui_pergunta_" + pergunta.id + "_recyclerview")
        textViewAndamento.setTag("ui_pergunta_" + pergunta.id + "_textview")
        linearLayoutAdicionar.setTag("ui_pergunta_" + pergunta.id + "_linearlayout")

        recyclerView.setCustomAdapter(adapter, true)

        val functionAdicionarItem = { idPerguntaHandler: Int, duasLinhas: DuasLinhas ->
            if (configuracao.editavel && idPerguntaHandler == pergunta.id) {
                adapter.adicionar(duasLinhas)
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
        val listaResposta: List<DuasLinhas> = adapter.getLista()
        val resposta = Resposta(respostas = listaResposta)
        pergunta.resposta = resposta
        return resposta
    }

    override fun isPreenchidoCorretamente(): Boolean {
        val countItens = adapter.getLista().size
        val isPreenchidoCorretamente = countItens >= pergunta.getLimiteMinimo() && countItens <= pergunta.getLimiteMaximo()
        return isPreenchidoCorretamente
    }

    override fun getMensagemErroPreenchimento(): String {
        return String.format(contextActivity.getString(R.string.faltam_x_itens), (pergunta.getLimiteMaximo() - adapter.getLista().size))
    }

}