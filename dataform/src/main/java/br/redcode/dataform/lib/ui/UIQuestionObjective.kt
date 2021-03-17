package br.redcode.dataform.lib.ui

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import br.com.redcode.spinnable.library.extensions_functions.select
import br.redcode.dataform.lib.R
import br.redcode.dataform.lib.adapter.AdapterRadioButton
import br.redcode.dataform.lib.domain.UIQuestionBase
import br.redcode.dataform.lib.extension.setCustomAdapter
import br.redcode.dataform.lib.interfaces.Questionable
import br.redcode.dataform.lib.model.Answer
import br.redcode.dataform.lib.model.FormSettings
import br.redcode.dataform.lib.model.Question
import br.redcode.dataform.lib.utils.Constants
import br.redcode.dataform.lib.utils.Constants.INVALID_VALUE
import br.redcode.dataform.lib.utils.Constants.PREFFIX_QUESTION
import br.redcode.dataform.lib.utils.Constants.SUFFIX_QUESTION_RECYCLERVIEW
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

/**
 * Created by pedrofsn on 31/10/2017.
 */
class UIQuestionObjective(question: Question, settings: FormSettings) :
    UIQuestionBase(R.layout.ui_question_list, question, settings), Questionable {

    private lateinit var recyclerView: RecyclerView

    private lateinit var adapter: AdapterRadioButton
    private var indexSelected: Int = Constants.INVALID_VALUE

    override fun initView(view: View) {
        super.initView(view)
        recyclerView = view.findViewById(R.id.recyclerView)
        adapter = AdapterRadioButton(settings, onItemClickListener)
    }

    override fun populateView() {
        super.populateView()
        recyclerView.tag = "$PREFFIX_QUESTION${question.id}$SUFFIX_QUESTION_RECYCLERVIEW"

        question.options?.let {
            adapter.setLista(it)
            recyclerView.setCustomAdapter(adapter)
        }
    }

    override fun fillAnswer(answer: Answer) {
        super.fillAnswer(answer)
        launch(main()) { fillAnswerAsync(answer) }
    }

    private suspend fun fillAnswerAsync(answer: Answer) = coroutineScope {
        val asyncIndex = async(io()) {
            var indexAdapter = INVALID_VALUE

            val selectedId = answer.options?.firstOrNull()
            if (selectedId?.isNotBlank() == true) {
                indexAdapter = adapter.getList().indexOfFirst { selectedId == it.id }
            }

            return@async indexAdapter
        }

        val index = asyncIndex.await()

        if (index != INVALID_VALUE) {
            indexSelected = index
            adapter.getList().select(index)

            if (isInputAnswersInOtherScreen().not()) {
                adapter.notifyDataSetChanged()
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
        val answer = tempAnswer

        if (indexSelected != Constants.INVALID_VALUE) {
            answer.options = listOf(adapter.getList()[indexSelected].id)
        }

        return answer
    }

    override fun isFilledCorrect() = indexSelected != Constants.INVALID_VALUE
    override fun getMessageErrorFill() =
        recyclerView.context.getString(R.string.select_at_least_one_option)
}