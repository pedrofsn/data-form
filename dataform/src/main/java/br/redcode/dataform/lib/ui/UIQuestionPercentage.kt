package br.redcode.dataform.lib.ui

import android.view.View
import android.widget.SeekBar
import android.widget.TextView
import br.redcode.dataform.lib.R
import br.redcode.dataform.lib.domain.UIQuestionBase
import br.redcode.dataform.lib.interfaces.Questionable
import br.redcode.dataform.lib.model.Answer
import br.redcode.dataform.lib.model.FormSettings
import br.redcode.dataform.lib.model.Question
import br.redcode.dataform.lib.utils.Constants.EMPTY_STRING
import br.redcode.dataform.lib.utils.Constants.PREFFIX_QUESTION
import br.redcode.dataform.lib.utils.Constants.SUFFIX_QUESTION_SEEKBAR
import br.redcode.dataform.lib.utils.Constants.SUFFIX_QUESTION_TEXTVIEW

/**
 * Created by pedrofsn on 31/10/2017.
 */
class UIQuestionPercentage(question: Question, settings: FormSettings) :
    UIQuestionBase(R.layout.ui_question_percentage, question, settings), Questionable {

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

        updateText(0)

        seekBar.isEnabled = settings.editable

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                updateText(progress)
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
            }
        })
    }

    private fun updateText(progress: Int) {
        textView.text = progress.toString().plus("%")
    }

    override fun fillAnswer(answer: Answer) {
        super.fillAnswer(answer)

        answer.percentage?.let {
            try {
                updateText(it)
                seekBar.progress = it
            } catch (e: Exception) {

            }
        }
    }

    override fun getAnswer(): Answer {
        val answer = tempAnswer
        answer.percentage = seekBar.progress
        return answer
    }

    override fun isFilledCorrect() = true
    override fun getMessageErrorFill() = EMPTY_STRING
}