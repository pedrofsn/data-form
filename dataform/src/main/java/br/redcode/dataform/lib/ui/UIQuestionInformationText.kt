package br.redcode.dataform.lib.ui

import android.view.View
import android.widget.TextView
import br.redcode.dataform.lib.R
import br.redcode.dataform.lib.domain.UIQuestionBase
import br.redcode.dataform.lib.interfaces.Questionable
import br.redcode.dataform.lib.model.Answer
import br.redcode.dataform.lib.model.Question
import br.redcode.dataform.lib.model.QuestionSettings
import br.redcode.dataform.lib.utils.Constants.EMPTY_STRING
import br.redcode.dataform.lib.utils.Constants.PREFFIX_QUESTION
import br.redcode.dataform.lib.utils.Constants.SUFFIX_QUESTION_TEXTVIEW

/**
 * Created by pedrofsn on 31/10/2017.
 */
class UIQuestionInformationText(question: Question, configuracao: QuestionSettings) : UIQuestionBase(R.layout.ui_question_informative_text, question, configuracao), Questionable {

    private lateinit var textViewInformativo: TextView

    override fun initView(view: View) {
        super.initView(view)
        textViewInformativo = view.findViewById(R.id.textViewInformativo)
    }

    override fun populateView() {
        super.populateView()
        textViewInformativo.tag = "$PREFFIX_QUESTION${question.id}$SUFFIX_QUESTION_TEXTVIEW"
        question.information?.let { textViewInformativo.text = it }
    }

    override fun getAnswer(): Answer {
        val resposta = Answer()
        if (question.answer != null) resposta.tag = question.answer?.tag
        return resposta
    }

    override fun isFilledCorrect() = true

    override fun getMessageErrorFill() = EMPTY_STRING

}