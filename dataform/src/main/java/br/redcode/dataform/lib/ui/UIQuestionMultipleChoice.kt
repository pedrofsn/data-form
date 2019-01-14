package br.redcode.dataform.lib.ui

import android.view.View
import br.redcode.dataform.lib.R
import br.redcode.dataform.lib.adapter.AdapterCheckBox
import br.redcode.dataform.lib.domain.UIPerguntaGeneric
import br.redcode.dataform.lib.extension.setCustomAdapter
import br.redcode.dataform.lib.interfaces.Questionable
import br.redcode.dataform.lib.model.Answer
import br.redcode.dataform.lib.model.Question
import br.redcode.dataform.lib.model.QuestionSettings
import br.redcode.dataform.lib.utils.Constants.PREFFIX_QUESTION
import br.redcode.dataform.lib.utils.Constants.SUFFIX_QUESTION_RECYCLERVIEW

/**
 * Created by pedrofsn on 31/10/2017.
 */
class UIQuestionMultipleChoice(question: Question, configuracao: QuestionSettings) : UIPerguntaGeneric(R.layout.ui_question_list, question, configuracao), Questionable {

    private lateinit var recyclerView: androidx.recyclerview.widget.RecyclerView

    private lateinit var adapter: AdapterCheckBox

    override fun initView(view: View) {
        super.initView(view)
        recyclerView = view.findViewById(R.id.recyclerView)
    }

    override fun populateView() {
        super.populateView()
        recyclerView.tag = "$PREFFIX_QUESTION${question.id}$SUFFIX_QUESTION_RECYCLERVIEW"
        adapter = AdapterCheckBox(configuracao, onItemClickListener)

        question.options?.let {
            adapter.setLista(it)
            recyclerView.setCustomAdapter(adapter)
        }

        // Resposta pré-preenchida
        question.answer?.options?.let {
            for (alternativa in adapter.getList()) {
                for (resposta in it) {
                    if (resposta.selected && resposta.id == alternativa.id) {
                        alternativa.selected = resposta.selected
                    }
                }
            }

            adapter.notifyDataSetChanged()
        }

    }

    private val onItemClickListener: ((Int) -> Unit)? = { position ->
        if (configuracao.editable) {
            question.options?.let {
                val estado = it.get(position).selected

                it.get(position).selected = estado.not()
                adapter.notifyDataSetChanged()
            }
        }
    }

    override fun getAnswer(): Answer {
        val resposta = Answer(options = adapter.getList())
        if (question.answer != null) resposta.tag = question.answer?.tag
        question.answer = resposta
        return resposta
    }

    override fun isFilledCorrect(): Boolean {
        val countMarcadas = getQuantidadeAlternativasMarcadas()
        return countMarcadas >= question.getLimitMin() && countMarcadas <= question.getLimitMax()
    }

    fun getQuantidadeAlternativasMarcadas(): Int {
        return getAnswer().options?.filter { it.selected }?.size ?: 0
    }

    override fun getMessageErrorFill(): String {
        return String.format(recyclerView.context.getString(R.string.faltam_x_itens), (question.getLimitMax() - getQuantidadeAlternativasMarcadas()))
    }

}