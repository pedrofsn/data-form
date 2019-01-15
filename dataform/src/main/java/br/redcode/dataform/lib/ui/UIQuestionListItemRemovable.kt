package br.redcode.dataform.lib.ui

import android.view.View
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import br.com.redcode.spinnable.library.model.Spinnable
import br.redcode.dataform.lib.R
import br.redcode.dataform.lib.adapter.AdapterItemRemovible
import br.redcode.dataform.lib.domain.UIQuestionBase
import br.redcode.dataform.lib.domain.handlers.HandlerInputPopup
import br.redcode.dataform.lib.extension.setCustomAdapter
import br.redcode.dataform.lib.interfaces.Questionable
import br.redcode.dataform.lib.model.Answer
import br.redcode.dataform.lib.model.FormSettings
import br.redcode.dataform.lib.model.Question
import br.redcode.dataform.lib.utils.Constants.PREFFIX_QUESTION
import br.redcode.dataform.lib.utils.Constants.SUFFIX_QUESTION_LINEAR_LAYOUT
import br.redcode.dataform.lib.utils.Constants.SUFFIX_QUESTION_RECYCLERVIEW
import br.redcode.dataform.lib.utils.Constants.SUFFIX_QUESTION_TEXTVIEW


/**
 * Created by pedrofsn on 31/10/2017.
 */
class UIQuestionListItemRemovable(question: Question, settings: FormSettings, private val handlerInputPopup: HandlerInputPopup) : UIQuestionBase(R.layout.ui_question_list_item_removable, question, settings), Questionable {

    private lateinit var recyclerView: androidx.recyclerview.widget.RecyclerView
    private lateinit var textViewAndamento: TextView
    private lateinit var linearLayoutAdicionar: LinearLayout
    private lateinit var relativeLayout: RelativeLayout

    private val adapter = AdapterItemRemovible(settings) { position: Int -> removerItemDaLista(position) }

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

        recyclerView.setCustomAdapter(adapter, true)

        val functionAdicionarItem = { idPerguntaHandler: Long, spinnable: Spinnable ->
            if (settings.editable && idPerguntaHandler == question.id) {
                adapter.adicionar(spinnable)
                atualizarContador()
            }
        }
        linearLayoutAdicionar.setOnClickListener { handlerInputPopup.chamarPopup(question.id, functionAdicionarItem) }

        question.answer?.options?.let {
            adapter.setLista(it)
        }

        atualizarContador()
    }

    private fun removerItemDaLista(position: Int) {
        if (settings.editable) {
            adapter.remove(position)
            atualizarContador()
        }
    }

    private fun atualizarContador() {
        val tamanho = adapter.itemCount
        val maximo = question.getLimitMax()
        textViewAndamento.text = String.format(recyclerView.context.getString(R.string.x_barra_x), tamanho, maximo)

        linearLayoutAdicionar.isEnabled = tamanho != maximo
        if (isFilledCorrect()) uiIndicator.hide()
        relativeLayout.visibility = if (settings.editable) View.VISIBLE else View.GONE
    }

    override fun getAnswer(): Answer {
        val resposta = Answer(options = ArrayList(adapter.getList()))
        if (question.answer != null) resposta.tag = question.answer?.tag
        question.answer = resposta
        return resposta
    }

    override fun isFilledCorrect() = adapter.getList().size in question.getLimitMax()..question.getLimitMin()
    override fun getMessageErrorFill() = String.format(recyclerView.context.getString(R.string.faltam_x_itens), (question.getLimitMax() - adapter.getList().size))

}