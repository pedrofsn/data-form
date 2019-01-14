package br.redcode.dataform.lib.ui

import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.OrientationHelper
import br.redcode.dataform.lib.R
import br.redcode.dataform.lib.adapter.AdapterImage
import br.redcode.dataform.lib.domain.UIPerguntaGeneric
import br.redcode.dataform.lib.domain.handlers.HandlerCaptureImage
import br.redcode.dataform.lib.extension.setCustomAdapter
import br.redcode.dataform.lib.interfaces.CallbackViewHolderImagem
import br.redcode.dataform.lib.model.Answer
import br.redcode.dataform.lib.model.Image
import br.redcode.dataform.lib.model.Question
import br.redcode.dataform.lib.model.QuestionSettings
import br.redcode.dataform.lib.utils.Constants.PREFFIX_QUESTION
import br.redcode.dataform.lib.utils.Constants.SUFFIX_QUESTION_LINEAR_LAYOUT
import br.redcode.dataform.lib.utils.Constants.SUFFIX_QUESTION_RECYCLERVIEW
import br.redcode.dataform.lib.utils.Constants.SUFFIX_QUESTION_TEXTVIEW

/**
 * Created by pedrofsn on 31/10/2017.
 */
class UIQuestionImage(question: Question, configuracao: QuestionSettings, val handlerCaptura: HandlerCaptureImage, val tipo: Tipo) : UIPerguntaGeneric(R.layout.ui_question_image, question, configuracao) {

    enum class Tipo {
        CAMERA, GALERIA, CAMERA_OU_GALERIA
    }

    private lateinit var recyclerView: androidx.recyclerview.widget.RecyclerView
    private lateinit var textViewAndamento: TextView
    private lateinit var linearLayoutAdicionar: LinearLayout
    private lateinit var relativeLayout: RelativeLayout

    val callback = object : CallbackViewHolderImagem {
        override fun removeImage(position: Int) {
            this@UIQuestionImage.removerImagem(position)
        }

        override fun visualizeImage(image: Image) {
            handlerCaptura.visualizarImagem(image)
        }

        override fun loadImage(image: String, imageView: ImageView) {
            handlerCaptura.carregarImagem(image, imageView)
        }

    }

    private val comLegenda = question.customSettings?.get("subtitle") ?: false
    private val adapter by lazy { AdapterImage(callback, configuracao, comLegenda) }

    override fun initView(view: View) {
        super.initView(view)
        recyclerView = view.findViewById(R.id.recyclerView)
        textViewAndamento = view.findViewById(R.id.textViewAndamento)
        linearLayoutAdicionar = view.findViewById(R.id.linearLayoutAdicionar)
        relativeLayout = view.findViewById(R.id.relativeLayout)
    }

    override fun populateView() {
        super.populateView()
        recyclerView.tag = "$PREFFIX_QUESTION${question.id}$SUFFIX_QUESTION_RECYCLERVIEW"
        textViewAndamento.tag = "$PREFFIX_QUESTION${question.id}$SUFFIX_QUESTION_TEXTVIEW"
        linearLayoutAdicionar.tag = "$PREFFIX_QUESTION${question.id}$SUFFIX_QUESTION_LINEAR_LAYOUT"

        if (configuracao.editable) {
            linearLayoutAdicionar.setOnClickListener { adicionarImagem() }
        }

        val layoutManagerHorizontal = LinearLayoutManager(recyclerView.context, OrientationHelper.HORIZONTAL, false)
        val layoutManagerVertical = LinearLayoutManager(recyclerView.context)
        recyclerView.setCustomAdapter(adapter, layoutManager = if (comLegenda) layoutManagerVertical else layoutManagerHorizontal)

        atualizarContador()

        // Resposta pré-preenchida
        question.answer?.images?.let {
            adapter.setLista(it)
            adapter.notifyDataSetChanged()
            atualizarContador()
        }
    }

    private fun adicionarImagem() {
        if (handlerCaptura.hasPermissoes()) {
            if (canAddMoreOne()) {
                handlerCaptura.capturarImagem(this, tipo)
            }
        }
    }

    override fun getAnswer(): Answer {
        val answer = Answer()
        answer.images = adapter.getList()
        if (question.answer != null) answer.tag = question.answer?.tag
        question.answer = answer
        return answer
    }

    override fun isFilledCorrect(): Boolean {
        return isInMin() && isInMax()
    }

    override fun getMessageErrorFill(): String {
        return String.format(recyclerView.context.getString(R.string.faltam_x_itens), (question.getLimitMax() - getQuantityCurrent()))
    }

    private fun atualizarContador() {
        val tamanho = getQuantityCurrent()
        val maximo = question.getLimitMax()
        textViewAndamento.text = String.format(recyclerView.context.getString(R.string.x_barra_x), tamanho, maximo)

        linearLayoutAdicionar.isEnabled = tamanho != maximo
        if (isFilledCorrect()) UIIndicator.hide()

        recyclerView.visibility = if (tamanho > 0) View.VISIBLE else View.GONE
        relativeLayout.visibility = if (configuracao.editable) View.VISIBLE else View.GONE
    }

    fun adicionarImagem(image: Image) {
        if (configuracao.editable && canAddMoreOne()) {
            adapter.adicionar(image)
            adapter.notifyDataSetChanged()
            atualizarContador()
        }
    }

    private fun removerImagem(position: Int) {
        if (configuracao.editable) {
            adapter.remover(position)
            adapter.notifyDataSetChanged()
            atualizarContador()
        }
    }

    fun getQuantityCurrent() = adapter.itemCount
    fun isInMax() = getQuantityCurrent() <= question.getLimitMax()
    fun isInMin() = getQuantityCurrent() >= question.getLimitMin()
    private fun canAddMoreOne() = getQuantityCurrent() + 1 <= question.getLimitMax()

}