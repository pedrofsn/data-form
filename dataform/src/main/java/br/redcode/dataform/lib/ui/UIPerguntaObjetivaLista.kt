package br.redcode.dataform.lib.ui

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import br.redcode.dataform.lib.R
import br.redcode.dataform.lib.adapter.AdapterRadioButton
import br.redcode.dataform.lib.domain.UIPerguntaGeneric
import br.redcode.dataform.lib.extension.setCustomAdapter
import br.redcode.dataform.lib.interfaces.OnItemClickListener
import br.redcode.dataform.lib.interfaces.Perguntavel
import br.redcode.dataform.lib.model.Pergunta
import br.redcode.dataform.lib.model.Resposta
import br.redcode.dataform.lib.utils.Constantes

/**
 * Created by pedrofsn on 31/10/2017.
 */
class UIPerguntaObjetivaLista(val contextActivity: Context, pergunta: Pergunta) : UIPerguntaGeneric(contextActivity, R.layout.ui_pergunta_objetiva_lista, pergunta), Perguntavel, OnItemClickListener {

    private lateinit var recyclerView: RecyclerView

    private lateinit var adapter: AdapterRadioButton
    private var posicaoSelecionada: Int = Constantes.VALOR_INVALIDO

    override fun initView(view: View) {
        super.initView(view)
        recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        adapter = AdapterRadioButton(this)
    }

    override fun populateView() {
        super.populateView()

        pergunta.alternativas?.let {
            adapter.setLista(it)
            recyclerView.setCustomAdapter(adapter)
        }

        pergunta.resposta?.alternativa?.let {
            for (i in adapter.getLista().indices) {
                if (it.selecionado && it.id == adapter.getLista().get(i).id) {
                    posicaoSelecionada = i
                    adapter.getLista().get(i).selecionado = true
                    adapter.notifyDataSetChanged()
                    break
                }
            }
        }
    }

    override fun onItemClickListener(position: Int) {
        pergunta.alternativas?.let {
            val estado = it.get(position).selecionado

            for (alternativa in it) {
                alternativa.selecionado = false
            }

            it.get(position).selecionado = estado.not()
            posicaoSelecionada = if (estado.not()) {
                position
            } else {
                Constantes.VALOR_INVALIDO
            }

            adapter.notifyDataSetChanged()
        }
    }

    override fun getResposta(): Resposta {
        val resposta: Resposta = if (posicaoSelecionada != Constantes.VALOR_INVALIDO) {
            Resposta(alternativa = adapter.getLista().get(posicaoSelecionada))
        } else {
            Resposta()
        }

        pergunta.resposta = resposta
        return resposta
    }

    override fun isPreenchidoCorretamente(): Boolean {
        return posicaoSelecionada != Constantes.VALOR_INVALIDO
    }

    override fun getMensagemErroPreenchimento(): String {
        return contextActivity.getString(R.string.selecione_ao_menos_uma_alternativa)
    }

}