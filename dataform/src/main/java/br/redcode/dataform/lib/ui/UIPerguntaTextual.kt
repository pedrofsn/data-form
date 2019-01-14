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
import br.redcode.dataform.lib.model.ConfiguracaoFormulario
import br.redcode.dataform.lib.model.Pergunta
import br.redcode.dataform.lib.model.Resposta

/**
 * Created by pedrofsn on 31/10/2017.
 */
class UIPerguntaTextual(private val contextActivity: Context, pergunta: Pergunta, configuracao: ConfiguracaoFormulario) : UIPerguntaGeneric(contextActivity, R.layout.ui_pergunta_textual, pergunta, configuracao), Questionable {

    private lateinit var editText: EditText

    override fun initView(view: View) {
        super.initView(view)
        editText = view.findViewById(R.id.editText)
    }

    override fun populateView() {
        super.populateView()
        editText.tag = "ui_pergunta_" + pergunta.id + "_edittext"
        pergunta.resposta?.resposta?.let { editText.setText(it) }
        editText.isEnabled = configuracao.editavel
        if (configuracao.editavel.not()) {
            // TODO try to migrete from this code bellow to TextViewCompat.setTextAppearance(editText, android.R.style.TextAppearance_Widget_TextView) (FIX: https://stackoverflow.com/a/37028325/1565769)
            @Suppress("DEPRECATION")
            editText.setTextAppearance(contextActivity, android.R.style.TextAppearance_Widget_TextView)
            editText.setTypeface(null, Typeface.ITALIC)
        }

        if (pergunta.isPerguntaTextual() && pergunta.tipo != null) {
            when {
                "email" == pergunta.tipo -> editText.inputType = InputType.TYPE_TEXT_VARIATION_WEB_EMAIL_ADDRESS
                "numero" == pergunta.tipo -> editText.inputType = InputType.TYPE_CLASS_NUMBER
                "multi" == pergunta.tipo -> {
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

    override fun getAnswer(): Resposta {
        val resposta = Resposta(resposta = editText.text.toString().trim())
        if (pergunta.resposta != null) resposta.tag = pergunta.resposta?.tag
        pergunta.resposta = resposta
        return resposta
    }

    override fun isFilledCorrect() = editText.text.toString().trim().isNotEmpty()
    override fun getMessageErrorFill() = contextActivity.getString(R.string.o_campo_de_texto_nao_foi_preenchido)

}