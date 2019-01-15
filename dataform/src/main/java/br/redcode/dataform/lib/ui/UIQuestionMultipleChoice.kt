package br.redcode.dataform.lib.ui

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import br.redcode.dataform.lib.R
import br.redcode.dataform.lib.adapter.AdapterCheckBox
import br.redcode.dataform.lib.domain.UIQuestionBase
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
class UIQuestionMultipleChoice(question: Question, configuracao: QuestionSettings) : UIQuestionBase(R.layout.ui_question_list, question, configuracao), Questionable {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: AdapterCheckBox

    override fun initView(view: View) {
        super.initView(view)
        recyclerView = view.findViewById(R.id.recyclerView)
    }

    override fun populateView() {
        super.populateView()
        recyclerView.tag = "$PREFFIX_QUESTION${question.id}$SUFFIX_QUESTION_RECYCLERVIEW"
        adapter = AdapterCheckBox(settings, onItemClickListener)

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
        val answer = Answer(options = adapter.getList())
        if (question.answer != null) answer.tag = question.answer?.tag
        question.answer = answer
        return answer
    }

    override fun isFilledCorrect(): Boolean {
        val selecteds = getQuantitySelecteds()
        return selecteds >= question.getLimitMin() && selecteds <= question.getLimitMax()
    }

    private fun getQuantitySelecteds() = getAnswer().options?.count { it.selected } ?: 0
    override fun getMessageErrorFill() = String.format(recyclerView.context.getString(R.string.faltam_x_itens), (question.getLimitMax() - getQuantitySelecteds()))

}