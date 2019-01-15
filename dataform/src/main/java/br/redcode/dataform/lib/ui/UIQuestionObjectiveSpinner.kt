package br.redcode.dataform.lib.ui

import android.view.View
import android.widget.Spinner
import br.com.redcode.spinnable.library.extensions_functions.getSpinnableFromSpinner
import br.com.redcode.spinnable.library.model.Spinnable
import br.redcode.dataform.lib.R
import br.redcode.dataform.lib.domain.UIQuestionBase
import br.redcode.dataform.lib.extension.setSpinnable2
import br.redcode.dataform.lib.interfaces.Questionable
import br.redcode.dataform.lib.model.Answer
import br.redcode.dataform.lib.model.Question
import br.redcode.dataform.lib.model.QuestionSettings
import br.redcode.dataform.lib.utils.Constants.PREFFIX_QUESTION
import br.redcode.dataform.lib.utils.Constants.SUFFIX_QUESTION_SPINNER
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

/**
 * Created by pedrofsn on 31/10/2017.
 */
class UIQuestionObjectiveSpinner(question: Question, settings: QuestionSettings) : UIQuestionBase(R.layout.ui_question_objective_spinner, question, settings), Questionable {

    private lateinit var spinner: Spinner

    override fun initView(view: View) {
        super.initView(view)
        spinner = view.findViewById<Spinner>(R.id.spinner)
    }

    override fun populateView() {
        super.populateView()
        spinner.tag = "$PREFFIX_QUESTION${question.id}$SUFFIX_QUESTION_SPINNER"

        launch(main()) { fillAnswer() }
    }

    private suspend fun fillAnswer() = coroutineScope() {
        val asyncId = async(io()) {
            return@async question.answer?.options?.firstOrNull { it.selected }?.id
        }

        val id = asyncId.await()
        val questionOptions = question.options?.toList() ?: emptyList()

        spinner.setSpinnable2(questionOptions, true, id)
        spinner.isEnabled = settings.editable
    }

    override fun getAnswer(): Answer {
        val spinnable = spinner.getSpinnableFromSpinner(question.options)
        val resposta = Answer()

        if (spinnable != null) {
            val result = arrayListOf<Spinnable>()
            result.add(spinnable)

            spinnable.selected = true
            resposta.options = result
        }

        if (question.answer != null) resposta.tag = question.answer?.tag
        question.answer = resposta
        return resposta
    }

    override fun isFilledCorrect() = spinner.selectedItemPosition != 0 || spinner.visibility == View.GONE
    override fun getMessageErrorFill() = spinner.context.getString(R.string.selecione_ao_menos_uma_alternativa)

}