package br.redcode.dataform.lib.ui

import android.content.Context
import android.view.View
import br.redcode.dataform.lib.R
import br.redcode.dataform.lib.adapter.AdapterCheckBox
import br.redcode.dataform.lib.domain.UIPerguntaGeneric
import br.redcode.dataform.lib.extension.setCustomAdapter
import br.redcode.dataform.lib.interfaces.OnItemClickListener
import br.redcode.dataform.lib.interfaces.Perguntavel
import br.redcode.dataform.lib.model.ConfiguracaoFormulario
import br.redcode.dataform.lib.model.Pergunta
import br.redcode.dataform.lib.model.Resposta

/**
 * Created by pedrofsn on 31/10/2017.
 */
class UIPerguntaMultiplaEscolha(val contextActivity: Context, pergunta: Pergunta, configuracao: ConfiguracaoFormulario) : UIPerguntaGeneric(contextActivity, R.layout.ui_pergunta_objetiva_lista, pergunta, configuracao), Perguntavel, OnItemClickListener {

    private lateinit var recyclerView: androidx.recyclerview.widget.RecyclerView

    private lateinit var adapter: AdapterCheckBox

    override fun initView(view: View) {
        super.initView(view)
        recyclerView = view.findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.recyclerView)
    }

    override fun populateView() {
        super.populateView()
        recyclerView.setTag("ui_pergunta_" + pergunta.id + "_recyclerview")
        adapter = AdapterCheckBox(this, configuracao)

        pergunta.alternativas?.let {
            adapter.setLista(it)
            recyclerView.setCustomAdapter(adapter)
        }

        // Resposta pré-preenchida
        pergunta.resposta?.alternativas?.let {
            for (alternativa in adapter.getLista()) {
                for (resposta in it) {
                    if (resposta.selected && resposta.id == alternativa.id) {
                        alternativa.selected = resposta.selected
                    }
                }
            }

            adapter.notifyDataSetChanged()
        }

    }

    override fun onItemClickListener(position: Int) {
        if (configuracao.editavel) {
            pergunta.alternativas?.let {
                val estado = it.get(position).selected

                it.get(position).selected = estado.not()
                adapter.notifyDataSetChanged()
            }
        }
    }

    override fun getResposta(): Resposta {
        val resposta = Resposta(alternativas = adapter.getLista())
        if (pergunta.resposta != null) resposta.tag = pergunta.resposta?.tag
        pergunta.resposta = resposta
        return resposta
    }

    override fun isPreenchidoCorretamente(): Boolean {
        val countMarcadas = getQuantidadeAlternativasMarcadas()
        return countMarcadas >= pergunta.getLimiteMinimo() && countMarcadas <= pergunta.getLimiteMaximo()
    }

    fun getQuantidadeAlternativasMarcadas(): Int {
        return getResposta().alternativas?.filter { it.selected }?.size ?: 0
    }

    override fun getMensagemErroPreenchimento(): String {
        return String.format(contextActivity.getString(R.string.faltam_x_itens), (pergunta.getLimiteMaximo() - getQuantidadeAlternativasMarcadas()))
    }

}