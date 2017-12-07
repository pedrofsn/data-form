package br.redcode.dataform.lib.ui

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.OrientationHelper
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import br.redcode.dataform.lib.R
import br.redcode.dataform.lib.adapter.AdapterImagem
import br.redcode.dataform.lib.domain.UIPerguntaGeneric
import br.redcode.dataform.lib.domain.handlers.HandlerCapturaImagem
import br.redcode.dataform.lib.extension.setCustomAdapter
import br.redcode.dataform.lib.interfaces.CallbackViewHolderImagem
import br.redcode.dataform.lib.model.ConfiguracaoFormulario
import br.redcode.dataform.lib.model.Imagem
import br.redcode.dataform.lib.model.Pergunta
import br.redcode.dataform.lib.model.Resposta

/**
 * Created by pedrofsn on 31/10/2017.
 */
class UIPerguntaImagem(val contextActivity: Context, pergunta: Pergunta, configuracao: ConfiguracaoFormulario, val handlerCaptura: HandlerCapturaImagem, val tipo: Tipo) : UIPerguntaGeneric(contextActivity, R.layout.ui_pergunta_imagem, pergunta, configuracao) {

    enum class Tipo {
        CAMERA, GALERIA, CAMERA_OU_GALERIA
    }

    private lateinit var recyclerView: RecyclerView
    private lateinit var textViewAndamento: TextView
    private lateinit var linearLayoutAdicionar: LinearLayout
    private lateinit var relativeLayout: RelativeLayout

    val callback = object : CallbackViewHolderImagem {
        override fun removerImagem(posicao: Int) {
            this@UIPerguntaImagem.removerImagem(posicao)
        }

        override fun visualizarImagem(imagem: Imagem) {
            handlerCaptura.visualizarImagem(imagem)
        }

        override fun carregarImagem(imagem: String, imageView: ImageView) {
            handlerCaptura.carregarImagem(imagem, imageView)
        }

    }

    private val comLegenda = pergunta.configuracaoPergunta?.get("legenda") ?: false
    private val adapter by lazy { AdapterImagem(callback, configuracao, comLegenda) }

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

        if (configuracao.editavel) {
            linearLayoutAdicionar.setOnClickListener { adicionarImagem() }
        }

        val layoutManagerHorizontal = LinearLayoutManager(contextActivity, OrientationHelper.HORIZONTAL, false)
        val layoutManagerVertical = LinearLayoutManager(context)
        recyclerView.setCustomAdapter(adapter, layoutManager = if (comLegenda) layoutManagerVertical else layoutManagerHorizontal)

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
        if (pergunta.resposta != null) resposta.tag = pergunta.resposta?.tag
        pergunta.resposta = resposta
        return resposta
    }

    override fun isPreenchidoCorretamente(): Boolean {
        return isDentroDoLimiteMinimo() && isDentroDoLimiteMaximo()
    }

    override fun getMensagemErroPreenchimento(): String {
        return String.format(contextActivity.getString(R.string.faltam_x_itens), (pergunta.getLimiteMaximo() - getQuantidadeAtual()))
    }

    private fun atualizarContador() {
        val tamanho = getQuantidadeAtual()
        val maximo = pergunta.getLimiteMaximo()
        textViewAndamento.text = String.format(contextActivity.getString(R.string.x_barra_x), tamanho, maximo)

        linearLayoutAdicionar.isEnabled = tamanho != maximo
        if (isPreenchidoCorretamente()) indicador.hide()

        recyclerView.visibility = if (tamanho > 0) View.VISIBLE else View.GONE
        relativeLayout.visibility = if (configuracao.editavel) View.VISIBLE else View.GONE
    }

    fun adicionarImagem(imagem: Imagem) {
        if (configuracao.editavel && canAdicionarMaisUmaImagem()) {
            adapter.adicionar(imagem)
            adapter.notifyDataSetChanged()
            atualizarContador()
        }
    }

    private fun canAdicionarMaisUmaImagem(): Boolean {
        return getQuantidadeAtual() + 1 <= pergunta.getLimiteMaximo()
    }

    private fun removerImagem(position: Int) {
        if (configuracao.editavel) {
            adapter.remover(position)
            adapter.notifyDataSetChanged()
            atualizarContador()
        }
    }

    fun getQuantidadeAtual(): Int {
        return adapter.itemCount
    }

    fun isDentroDoLimiteMaximo(): Boolean {
        return getQuantidadeAtual() <= pergunta.getLimiteMaximo()
    }

    fun isDentroDoLimiteMinimo(): Boolean {
        return getQuantidadeAtual() >= pergunta.getLimiteMinimo()
    }

}