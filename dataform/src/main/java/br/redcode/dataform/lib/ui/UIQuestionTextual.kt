package br.redcode.dataform.lib.ui

import android.content.Context
import android.graphics.Typeface
import android.text.InputType
import android.text.method.ScrollingMovementMethod
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import br.redcode.dataform.lib.R
import br.redcode.dataform.lib.domain.UIPerguntaGeneric
import br.redcode.dataform.lib.interfaces.Questionable
import br.redcode.dataform.lib.model.Answer
import br.redcode.dataform.lib.model.Question
import br.redcode.dataform.lib.model.QuestionSettings
import br.redcode.dataform.lib.utils.Constants.FORMAT_QUESTION_TEXTUAL_EMAIL
import br.redcode.dataform.lib.utils.Constants.FORMAT_QUESTION_TEXTUAL_MULTI
import br.redcode.dataform.lib.utils.Constants.FORMAT_QUESTION_TEXTUAL_NUMBER
import br.redcode.dataform.lib.utils.Constants.PREFFIX_QUESTION
import br.redcode.dataform.lib.utils.Constants.SUFFIX_QUESTION_EDITTEXT

/**
 * Created by pedrofsn on 31/10/2017.
 */
class UIQuestionTextual(private val contextActivity: Context, question: Question, configuracao: QuestionSettings) : UIPerguntaGeneric(contextActivity, R.layout.ui_question_textual, question, configuracao), Questionable {

    private lateinit var editText: EditText

    override fun initView(view: View) {
        super.initView(view)
        editText = view.findViewById(R.id.editText)
    }

    override fun populateView() {
        super.populateView()
        editText.tag = "$PREFFIX_QUESTION${question.id}$SUFFIX_QUESTION_EDITTEXT"
        question.answer?.answer?.let { editText.setText(it) }
        editText.isEnabled = configuracao.editable
        if (configuracao.editable.not()) {
            // TODO try to migrete from this code bellow to TextViewCompat.setTextAppearance(editText, android.R.style.TextAppearance_Widget_TextView) (FIX: https://stackoverflow.com/a/37028325/1565769)
            @Suppress("DEPRECATION")
            editText.setTextAppearance(editText.context, android.R.style.TextAppearance_Widget_TextView)
            editText.setTypeface(null, Typeface.ITALIC)
        }

        if (question.isQuestionTextual() && question.format != null) {
            when {
                FORMAT_QUESTION_TEXTUAL_EMAIL == question.format -> editText.inputType = InputType.TYPE_TEXT_VARIATION_WEB_EMAIL_ADDRESS
                FORMAT_QUESTION_TEXTUAL_NUMBER == question.format -> editText.inputType = InputType.TYPE_CLASS_NUMBER
                FORMAT_QUESTION_TEXTUAL_MULTI == question.format -> {
                    editText.setSingleLine(false)
                    editText.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_MULTI_LINE
                    editText.imeOptions = EditorInfo.IME_FLAG_NO_ENTER_ACTION
                    editText.setLines(5)
                    editText.maxLines = 10
                    editText.isVerticalScrollBarEnabled = true
                    editText.movementMethod = ScrollingMovementMethod.getInstance()
                    editText.scrollBarStyle = View.SCROLLBARS_INSIDE_INSET
                }
            }
        }
    }

    override fun getAnswer(): Answer {
        val resposta = Answer(answer = editText.text.toString().trim())
        if (question.answer != null) resposta.tag = question.answer?.tag
        question.answer = resposta
        return resposta
    }

    override fun isFilledCorrect() = editText.text.toString().trim().isNotEmpty()
    override fun getMessageErrorFill() = contextActivity.getString(R.string.o_campo_de_texto_nao_foi_preenchido)

}