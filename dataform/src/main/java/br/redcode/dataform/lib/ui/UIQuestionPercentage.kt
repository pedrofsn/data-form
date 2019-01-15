package br.redcode.dataform.lib.ui

import android.view.View
import android.widget.SeekBar
import android.widget.TextView
import br.redcode.dataform.lib.R
import br.redcode.dataform.lib.domain.UIQuestionBase
import br.redcode.dataform.lib.interfaces.Questionable
import br.redcode.dataform.lib.model.Answer
import br.redcode.dataform.lib.model.Question
import br.redcode.dataform.lib.model.QuestionSettings
import br.redcode.dataform.lib.utils.Constants
import br.redcode.dataform.lib.utils.Constants.PREFFIX_QUESTION
import br.redcode.dataform.lib.utils.Constants.SUFFIX_QUESTION_SEEKBAR
import br.redcode.dataform.lib.utils.Constants.SUFFIX_QUESTION_TEXTVIEW

/**
 * Created by pedrofsn on 31/10/2017.
 */
class UIQuestionPercentage(question: Question, settings: QuestionSettings) : UIQuestionBase(R.layout.ui_question_percentage, question, settings), Questionable {

    private lateinit var seekBar: SeekBar
    private lateinit var textView: TextView

    override fun initView(view: View) {
        super.initView(view)
        seekBar = view.findViewById(R.id.seekBar)
        textView = view.findViewById(R.id.textView)
    }

    override fun populateView() {
        super.populateView()
        seekBar.tag = "$PREFFIX_QUESTION${question.id}$SUFFIX_QUESTION_SEEKBAR"
        textView.tag = "$PREFFIX_QUESTION${question.id}$SUFFIX_QUESTION_TEXTVIEW"

        atualizarTexto(0)
        question.answer?.answer?.let {

            try {
                val percentual = it.toInt()
                atualizarTexto(percentual)
                seekBar.progress = percentual
            } catch (e: Exception) {

            }

        }

        seekBar.isEnabled = settings.editable

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                atualizarTexto(progress)
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {

            }

            override fun onStopTrackingTouch(p0: SeekBar?) {

            }
        })
    }

    private fun atualizarTexto(progress: Int) {
        textView.text = progress.toString().plus("%")
    }

    override fun getAnswer(): Answer {
        val resposta = Answer(answer = seekBar.progress.toString())
        if (question.answer != null) resposta.tag = question.answer?.tag
        question.answer = resposta
        return resposta
    }

    override fun isFilledCorrect(): Boolean {
        return true
    }

    override fun getMessageErrorFill(): String {
        return Constants.EMPTY_STRING
    }

}