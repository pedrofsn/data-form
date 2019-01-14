package br.redcode.dataform.lib.ui

import android.content.Context
import android.view.View
import android.widget.Spinner
import br.com.redcode.spinnable.library.extensions_functions.getSpinnableFromSpinner
import br.com.redcode.spinnable.library.extensions_functions.setSpinnable
import br.redcode.dataform.lib.R
import br.redcode.dataform.lib.domain.UIPerguntaGeneric
import br.redcode.dataform.lib.interfaces.Questionable
import br.redcode.dataform.lib.model.Answer
import br.redcode.dataform.lib.model.Question
import br.redcode.dataform.lib.model.QuestionSettings
import br.redcode.dataform.lib.utils.Constants.PREFFIX_QUESTION
import br.redcode.dataform.lib.utils.Constants.SUFFIX_QUESTION_SPINNER

/**
 * Created by pedrofsn on 31/10/2017.
 */
class UIQuestionObjectiveSpinner(private val contextActivity: Context, question: Question, configuracao: QuestionSettings) : UIPerguntaGeneric(contextActivity, R.layout.ui_question_objective_spinner, question, configuracao), Questionable {

    private lateinit var spinner: Spinner

    override fun initView(view: View) {
        super.initView(view)
        spinner = view.findViewById<Spinner>(R.id.spinner)
    }

    override fun populateView() {
        super.populateView()
        spinner.tag = "$PREFFIX_QUESTION${question.id}$SUFFIX_QUESTION_SPINNER"

        question.options?.let {
            val idPreSelecionado = question.answer?.option?.id.toString()
            spinner.setSpinnable(it, true, idPreSelecionado)
        }

        spinner.isEnabled = configuracao.editable
    }

    override fun getAnswer(): Answer {
        val spinnable = spinner.getSpinnableFromSpinner(question.options)
        val resposta = Answer()

        if (spinnable != null) {
            resposta.option = spinnable
            resposta.option?.selected = true
        }

        if (question.answer != null) resposta.tag = question.answer?.tag
        question.answer = resposta
        return resposta
    }

    override fun isFilledCorrect() = spinner.selectedItemPosition != 0 || spinner.visibility == View.GONE
    override fun getMessageErrorFill() = contextActivity.getString(R.string.selecione_ao_menos_uma_alternativa)

}