package br.redcode.dataform.lib.ui

import android.content.Context
import android.content.DialogInterface
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import br.redcode.dataform.lib.R
import br.redcode.dataform.lib.model.FormSettings
import br.redcode.dataform.lib.utils.Constants.EMPTY_STRING

/**
 * Created by pedrofsn on 03/11/2017.
 */
class UIIndicator : ImageView {

    private var isError = false
    private var message = EMPTY_STRING
    var settings = FormSettings()

    constructor(context: Context) : super(context) {
        initialize()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initialize()
    }

    constructor(context: Context, attrs: AttributeSet, style: Int) : super(context, attrs, style) {
        initialize()
    }

    private fun initialize() {
        hide()

        if (settings.hasIndicator()) {
            isError = false
            setOnClickListener { showAlert() }
        }
    }

    fun setError(msg: String) {
        if (msg.isNotEmpty() && settings.showIndicatorError) {
            isError = true
            message = msg
            show()
            setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_erro))
        }
    }

    fun setInformation(msg: String) {
        if (msg.isNotEmpty() && settings.showIndicatorInformation) {
            isError = false
            message = msg
            show()
            setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_informacao))
        }
    }

    private fun showAlert() {
        if (message.isNotEmpty()) {
            AlertDialog.Builder(context)
                    .setTitle(getTitle())
                    .setMessage(message)
                    .setPositiveButton(context.getString(R.string.ok), object : DialogInterface.OnClickListener {
                        override fun onClick(p0: DialogInterface?, p1: Int) {

                        }
                    })
                    .setCancelable(false)
                    .show()
        }
    }

    private fun getTitle() = context.getString(if (isError) R.string.erro else R.string.informacao)

    fun show() {
        visibility = View.VISIBLE
    }

    fun hide() {
        visibility = View.GONE
    }

}