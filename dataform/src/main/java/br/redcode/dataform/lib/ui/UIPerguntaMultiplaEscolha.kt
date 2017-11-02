package br.redcode.dataform.lib.ui

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.widget.TextView
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
class UIPerguntaMultiplaEscolha(contextActivity: Context, val pergunta: Pergunta) : UIPerguntaGeneric<Pergunta>(contextActivity, R.layout.ui_pergunta_objetiva), Perguntavel, OnItemClickListener {

    private lateinit var textViewLabel: TextView
    private lateinit var recyclerView: RecyclerView

    private lateinit var adapter: AdapterCheckBox

    override fun initView() {
        textViewLabel = view.findViewById<TextView>(R.id.textViewLabel)
        recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        adapter = AdapterCheckBox(this)
    }

    override fun populateView() {
        textViewLabel.setText(pergunta.descricao)

        pergunta.alternativas?.let {
            adapter.setLista(it)
            recyclerView.setCustomAdapter(adapter)
        }

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
        return true
    }

    override fun exibirAlerta(mensagem: String) {

    }

}