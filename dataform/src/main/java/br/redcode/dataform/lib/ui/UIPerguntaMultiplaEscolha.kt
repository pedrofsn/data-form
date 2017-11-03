package br.redcode.dataform.lib.ui

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import br.redcode.dataform.lib.R
import br.redcode.dataform.lib.adapter.AdapterCheckBox
import br.redcode.dataform.lib.domain.UIPerguntaGeneric
import br.redcode.dataform.lib.extension.setCustomAdapter
import br.redcode.dataform.lib.interfaces.OnItemClickListener
import br.redcode.dataform.lib.interfaces.Perguntavel
import br.redcode.dataform.lib.model.Pergunta
import br.redcode.dataform.lib.model.Resposta

/**
 * Created by pedrofsn on 31/10/2017.
 */
class UIPerguntaMultiplaEscolha(contextActivity: Context, pergunta: Pergunta) : UIPerguntaGeneric(contextActivity, R.layout.ui_pergunta_objetiva_lista, pergunta), Perguntavel, OnItemClickListener {

    private lateinit var recyclerView: RecyclerView

    private lateinit var adapter: AdapterCheckBox

    override fun initView(view: View) {
        super.initView(view)
        recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
    }

    override fun populateView() {
        super.populateView()
        adapter = AdapterCheckBox(this)

        pergunta.alternativas?.let {
            adapter.setLista(it)
            recyclerView.setCustomAdapter(adapter)
        }

        // Resposta pré-preenchida
        pergunta.resposta?.alternativas?.let {
            for (alternativa in adapter.getLista()) {
                for (resposta in it) {
                    if (resposta.selecionado && resposta.id == alternativa.id) {
                        alternativa.selecionado = resposta.selecionado
                    }
                }
            }

            adapter.notifyDataSetChanged()
        }

    }

    override fun onItemClickListener(position: Int) {
        pergunta.alternativas?.let {
            val estado = it.get(position).selecionado

            it.get(position).selecionado = estado.not()
            adapter.notifyDataSetChanged()
        }
    }

    override fun getResposta(): Resposta {
        val resposta = Resposta(alternativas = adapter.getLista())
        pergunta.resposta = resposta
        return resposta
    }

    override fun isPreenchidoCorretamente(): Boolean {
        return true // Permite que nenhuma alternativa seja marcada // TODO migrar pra pelo menos uma (NDA)
    }

    override fun getMensagemErroPreenchimento(): String {
//        return  contextActivity.getString(R.string.selecione_ao_menos_uma_alternativa) //TODO: tratar limites aqui
        return ""
    }

}