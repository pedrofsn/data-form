package br.redcode.dataform.lib.ui

import android.view.View
import br.redcode.dataform.lib.R
import br.redcode.dataform.lib.adapter.AdapterRadioButton
import br.redcode.dataform.lib.domain.UIPerguntaGeneric
import br.redcode.dataform.lib.extension.setCustomAdapter
import br.redcode.dataform.lib.interfaces.Questionable
import br.redcode.dataform.lib.model.Answer
import br.redcode.dataform.lib.model.Question
import br.redcode.dataform.lib.model.QuestionSettings
import br.redcode.dataform.lib.utils.Constants
import br.redcode.dataform.lib.utils.Constants.PREFFIX_QUESTION
import br.redcode.dataform.lib.utils.Constants.SUFFIX_QUESTION_RECYCLERVIEW

/**
 * Created by pedrofsn on 31/10/2017.
 */
class UIQuestionObjective(question: Question, settings: QuestionSettings) : UIPerguntaGeneric(R.layout.ui_question_list, question, settings), Questionable {

    private lateinit var recyclerView: androidx.recyclerview.widget.RecyclerView

    private lateinit var adapter: AdapterRadioButton
    private var indexSelected: Int = Constants.INVALID_VALUE

    override fun initView(view: View) {
        super.initView(view)
        recyclerView = view.findViewById(R.id.recyclerView)
        adapter = AdapterRadioButton(configuracao, onItemClickListener)
    }

    override fun populateView() {
        super.populateView()
        recyclerView.tag = "$PREFFIX_QUESTION${question.id}$SUFFIX_QUESTION_RECYCLERVIEW"

        question.options?.let {
            adapter.setLista(it)
            recyclerView.setCustomAdapter(adapter)
        }

        question.answer?.option?.let {
            for (i in adapter.getList().indices) {
                if (it.selected && it.id == adapter.getList().get(i).id) {
                    indexSelected = i
                    adapter.getList().get(i).selected = true
                    adapter.notifyDataSetChanged()
                    break
                }
            }
        }
    }

    private val onItemClickListener = { position: Int ->
        if (settings.editable) {
            question.options?.let {
                val estado = it.get(position).selected

                for (alternativa in it) {
                    alternativa.selected = false
                }

                it.get(position).selected = estado.not()
                indexSelected = if (estado.not()) {
                    position
                } else {
                    Constants.INVALID_VALUE
                }

                adapter.notifyDataSetChanged()
            }
        }
    }

    override fun getAnswer(): Answer {
        val answer: Answer = if (indexSelected != Constants.INVALID_VALUE) {
            Answer(option = adapter.getList().get(indexSelected))
        } else {
            Answer()
        }

        if (question.answer != null) answer.tag = question.answer?.tag
        question.answer = answer
        return answer
    }

    override fun isFilledCorrect() = indexSelected != Constants.INVALID_VALUE
    override fun getMessageErrorFill() = recyclerView.context.getString(R.string.selecione_ao_menos_uma_alternativa)

}