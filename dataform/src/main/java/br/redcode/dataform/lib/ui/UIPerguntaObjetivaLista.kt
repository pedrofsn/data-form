package br.redcode.dataform.lib.ui

import android.content.Context
import android.view.View
import br.redcode.dataform.lib.R
import br.redcode.dataform.lib.adapter.AdapterRadioButton
import br.redcode.dataform.lib.domain.UIPerguntaGeneric
import br.redcode.dataform.lib.extension.setCustomAdapter
import br.redcode.dataform.lib.interfaces.OnItemClickListener
import br.redcode.dataform.lib.interfaces.Perguntavel
import br.redcode.dataform.lib.model.ConfiguracaoFormulario
import br.redcode.dataform.lib.model.Pergunta
import br.redcode.dataform.lib.model.Resposta
import br.redcode.dataform.lib.utils.Constantes

/**
 * Created by pedrofsn on 31/10/2017.
 */
class UIPerguntaObjetivaLista(private val contextActivity: Context, pergunta: Pergunta, configuracao: ConfiguracaoFormulario) : UIPerguntaGeneric(contextActivity, R.layout.ui_pergunta_objetiva_lista, pergunta, configuracao), Perguntavel, OnItemClickListener {

    private lateinit var recyclerView: androidx.recyclerview.widget.RecyclerView

    private lateinit var adapter: AdapterRadioButton
    private var posicaoSelecionada: Int = Constantes.VALOR_INVALIDO

    override fun initView(view: View) {
        super.initView(view)
        recyclerView = view.findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.recyclerView)
        adapter = AdapterRadioButton(this, configuracao)
    }

    override fun populateView() {
        super.populateView()
        recyclerView.setTag("ui_pergunta_" + pergunta.id + "_recyclerview")

        pergunta.alternativas?.let {
            adapter.setLista(it)
            recyclerView.setCustomAdapter(adapter)
        }

        pergunta.resposta?.alternativa?.let {
            for (i in adapter.getLista().indices) {
                if (it.selected && it.id == adapter.getLista().get(i).id) {
                    posicaoSelecionada = i
                    adapter.getLista().get(i).selected = true
                    adapter.notifyDataSetChanged()
                    break
                }
            }
        }
    }

    override fun onItemClickListener(position: Int) {
        if (configuracao.editavel) {
            pergunta.alternativas?.let {
                val estado = it.get(position).selected

                for (alternativa in it) {
                    alternativa.selected = false
                }

                it.get(position).selected = estado.not()
                posicaoSelecionada = if (estado.not()) {
                    position
                } else {
                    Constantes.VALOR_INVALIDO
                }

                adapter.notifyDataSetChanged()
            }
        }
    }

    override fun getResposta(): Resposta {
        val resposta: Resposta = if (posicaoSelecionada != Constantes.VALOR_INVALIDO) {
            Resposta(alternativa = adapter.getLista().get(posicaoSelecionada))
        } else {
            Resposta()
        }

        if (pergunta.resposta != null) resposta.tag = pergunta.resposta?.tag
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