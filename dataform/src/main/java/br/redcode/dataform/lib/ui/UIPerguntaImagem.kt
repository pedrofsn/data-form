package br.redcode.dataform.lib.ui

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.OrientationHelper
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import br.redcode.dataform.lib.R
import br.redcode.dataform.lib.adapter.AdapterImagem
import br.redcode.dataform.lib.domain.UIPerguntaGeneric
import br.redcode.dataform.lib.extension.setCustomAdapter
import br.redcode.dataform.lib.interfaces.CallbackImagem
import br.redcode.dataform.lib.interfaces.OnItemClickListener
import br.redcode.dataform.lib.interfaces.Perguntavel
import br.redcode.dataform.lib.model.Imagem
import br.redcode.dataform.lib.model.Pergunta
import br.redcode.dataform.lib.model.Resposta

/**
 * Created by pedrofsn on 31/10/2017.
 */
class UIPerguntaImagem(val contextActivity: Context, val pergunta: Pergunta, val callback: CallbackImagem) : UIPerguntaGeneric<Pergunta>(contextActivity, R.layout.ui_pergunta_imagem), Perguntavel {

    private lateinit var textViewLabel: TextView
    private lateinit var recyclerView: RecyclerView
    private lateinit var textViewAndamento: TextView
    private lateinit var buttonAdicionar: Button


    private val adapter = AdapterImagem(object : OnItemClickListener {
        override fun onItemClickListener(position: Int) {
            previsualizarImagem(position)
        }
    }, object : OnItemClickListener {
        override fun onItemClickListener(position: Int) {
            removerImagem(position)
        }
    })

    override fun initView() {
        textViewLabel = view.findViewById<TextView>(R.id.textViewLabel)
        recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        textViewAndamento = view.findViewById<TextView>(R.id.textViewAndamento)
        buttonAdicionar = view.findViewById<Button>(R.id.buttonAdicionar)
    }

    override fun populateView() {
        textViewLabel.setText(pergunta.descricao)
        buttonAdicionar.setOnClickListener { adicionarImagem() }

        val layoutManagerHorizontal = LinearLayoutManager(contextActivity, OrientationHelper.HORIZONTAL, false)
        recyclerView.setCustomAdapter(adapter, layoutManager = layoutManagerHorizontal)

        atualizarContador()
    }

    private fun adicionarImagem() {
        if (hasPermissao()) {
            if (canAdicionarMaisUmaImagem()) {
                callback.capturarImagem(this)
            } else {
                exibirAlerta(contextActivity.getString(R.string.limite_maximo_atingido))
            }
        }
    }

    private fun hasPermissao(): Boolean {
        return true //TODO tratar permissões
    }

    override fun getResposta(): Resposta {
        val resposta = Resposta()
        resposta.imagens = adapter.getLista()
        pergunta.resposta = resposta
        return resposta
    }

    override fun isPreenchidoCorretamente(): Boolean {
        return pergunta.isDentroDoLimiteMinimo() && pergunta.isDentroDoLimiteMaximo()
    }

    private fun atualizarContador() {
        val tamanho = pergunta.getQuantidadeImagens()
        val maximo = pergunta.getLimiteMaximo()
        textViewAndamento.text = String.format(contextActivity.getString(R.string.x_barra_x), tamanho, maximo)
    }

    override fun exibirAlerta(mensagem: String) {
        Toast.makeText(contextActivity, mensagem, Toast.LENGTH_SHORT).show()
    }

    fun adicionarImagem(imagem: Imagem) {
        if (canAdicionarMaisUmaImagem()) {
            adapter.adicionar(imagem)
            adapter.notifyDataSetChanged()
            atualizarContador()
        }
    }

    private fun canAdicionarMaisUmaImagem(): Boolean {
        return pergunta.getQuantidadeImagens() + 1 <= pergunta.getLimiteMaximo()
    }

    private fun removerImagem(position: Int) {
        adapter.remover(position)
        adapter.notifyDataSetChanged()
        atualizarContador()
    }

    private fun previsualizarImagem(position: Int) {
        Log.e("tag", "Pré-visualizar ${position}") //TODO
    }

}