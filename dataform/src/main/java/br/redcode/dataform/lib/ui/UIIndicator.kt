package br.redcode.dataform.lib.ui

import android.content.Context
import android.content.DialogInterface
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import br.redcode.dataform.lib.R
import br.redcode.dataform.lib.model.QuestionSettings
import br.redcode.dataform.lib.utils.Constants.EMPTY_STRING

/**
 * Created by pedrofsn on 03/11/2017.
 */
class UIIndicator : ImageView {

    private var isError: Boolean = false
    private var message: String = EMPTY_STRING
    var configuracao: QuestionSettings = QuestionSettings()

    constructor(context: Context) : super(context) {
        inicializar()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        inicializar()
    }

    constructor(context: Context, attrs: AttributeSet, style: Int) : super(context, attrs, style) {
        inicializar()
    }

    private fun inicializar() {
        hide()

        if (configuracao.hasIndicator()) {
            isError = false
            setOnClickListener { exibirAlerta() }
        }
    }

    fun setErro(msg: String) {
        if (msg.isNotEmpty() && configuracao.showIndicatorError) {
            isError = true
            message = msg
            show()
            setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_erro))
        }
    }

    fun setInformacao(msg: String) {
        if (msg.isNotEmpty() && configuracao.showIndicatorInformation) {
            isError = false
            message = msg
            show()
            setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_informacao))
        }
    }

    private fun exibirAlerta() {
        if (message.isNotEmpty()) {
            AlertDialog.Builder(context)
                    .setTitle(context.getString(if (isError) R.string.erro else R.string.informacao))
                    .setMessage(message)
                    .setPositiveButton(context.getString(R.string.ok), object : DialogInterface.OnClickListener {
                        override fun onClick(p0: DialogInterface?, p1: Int) {

                        }
                    })
                    .setCancelable(false)
                    .show()

        }
    }

    fun show() {
        visibility = View.VISIBLE
    }

    fun hide() {
        visibility = View.GONE
    }

}