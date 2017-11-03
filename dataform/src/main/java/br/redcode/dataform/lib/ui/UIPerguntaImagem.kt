package br.redcode.dataform.lib.ui

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.OrientationHelper
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import br.redcode.dataform.lib.R
import br.redcode.dataform.lib.adapter.AdapterImagem
import br.redcode.dataform.lib.adapter.viewholder.ViewHolderImagem
import br.redcode.dataform.lib.domain.HandlerCapturaImagem
import br.redcode.dataform.lib.domain.UIPerguntaGeneric
import br.redcode.dataform.lib.extension.setCustomAdapter
import br.redcode.dataform.lib.model.Imagem
import br.redcode.dataform.lib.model.Pergunta
import br.redcode.dataform.lib.model.Resposta

/**
 * Created by pedrofsn on 31/10/2017.
 */
class UIPerguntaImagem(val contextActivity: Context, pergunta: Pergunta, val handlerCaptura: HandlerCapturaImagem, val tipo: Tipo) : UIPerguntaGeneric(contextActivity, R.layout.ui_pergunta_imagem, pergunta) {

    enum class Tipo {
        CAMERA, GALERIA, CAMERA_OU_GALERIA
    }

    private lateinit var recyclerView: RecyclerView
    private lateinit var textViewAndamento: TextView
    private lateinit var buttonAdicionar: Button

    private val adapter = AdapterImagem(object : ViewHolderImagem.CallbackViewHolderImagem {
        override fun removerImagem(posicao: Int) {
            this@UIPerguntaImagem.removerImagem(posicao)
        }

        override fun visualizarImagem(imagem: Imagem) {
            handlerCaptura.visualizarImagem(imagem)
        }

        override fun carregarImagem(imagem: String, imageView: ImageView) {
            handlerCaptura.carregarImagem(imagem, imageView)
        }

    })

    override fun initView(view: View) {
        super.initView(view)
        recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        textViewAndamento = view.findViewById<TextView>(R.id.textViewAndamento)
        buttonAdicionar = view.findViewById<Button>(R.id.buttonAdicionar)
    }

    override fun populateView() {
        super.populateView()
        buttonAdicionar.setOnClickListener { adicionarImagem() }

        val layoutManagerHorizontal = LinearLayoutManager(contextActivity, OrientationHelper.HORIZONTAL, false)
        recyclerView.setCustomAdapter(adapter, layoutManager = layoutManagerHorizontal)

        atualizarContador()

        // Resposta pré-preenchida
        pergunta.resposta?.imagens?.let {
            adapter.setLista(it)
            adapter.notifyDataSetChanged()
            atualizarContador()
        }

    }

    private fun adicionarImagem() {
        if (handlerCaptura.hasPermissoes()) {
            if (canAdicionarMaisUmaImagem()) {
                handlerCaptura.capturarImagem(this, tipo)
            }
        }
    }

    override fun getResposta(): Resposta {
        val resposta = Resposta()
        resposta.imagens = adapter.getLista()
        pergunta.resposta = resposta
        return resposta
    }

    override fun isPreenchidoCorretamente(): Boolean {
        return isDentroDoLimiteMinimo() && isDentroDoLimiteMaximo()
    }

    override fun getMensagemErroPreenchimento(): String {
        return String.format(contextActivity.getString(R.string.faltam_x_itens), (pergunta.getLimiteMaximo() - getQuantidadeImagens()))
    }

    private fun atualizarContador() {
        val tamanho = getQuantidadeImagens()
        val maximo = pergunta.getLimiteMaximo()
        textViewAndamento.text = String.format(contextActivity.getString(R.string.x_barra_x), tamanho, maximo)

        buttonAdicionar.isEnabled = tamanho != maximo
        if (isPreenchidoCorretamente()) indicador.hide()
    }

    fun adicionarImagem(imagem: Imagem) {
        if (canAdicionarMaisUmaImagem()) {
            adapter.adicionar(imagem)
            adapter.notifyDataSetChanged()
            atualizarContador()
        }
    }

    private fun canAdicionarMaisUmaImagem(): Boolean {
        return getQuantidadeImagens() + 1 <= pergunta.getLimiteMaximo()
    }

    private fun removerImagem(position: Int) {
        adapter.remover(position)
        adapter.notifyDataSetChanged()
        atualizarContador()
    }

    fun getQuantidadeImagens(): Int {
        return adapter.itemCount
    }

    fun isDentroDoLimiteMaximo(): Boolean {
        return getQuantidadeImagens() <= pergunta.getLimiteMaximo()
    }

    fun isDentroDoLimiteMinimo(): Boolean {
        return getQuantidadeImagens() >= pergunta.getLimiteMinimo()
    }

}