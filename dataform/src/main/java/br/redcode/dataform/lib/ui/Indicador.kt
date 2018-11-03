package br.redcode.dataform.lib.ui

import android.content.Context
import android.content.DialogInterface
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import br.redcode.dataform.lib.R
import br.redcode.dataform.lib.model.ConfiguracaoFormulario
import br.redcode.dataform.lib.utils.Constantes

/**
 * Created by pedrofsn on 03/11/2017.
 */
class Indicador : ImageView {

    private var isErro: Boolean = false
    private var mensagem: String = Constantes.STRING_VAZIA
    var configuracao: ConfiguracaoFormulario = ConfiguracaoFormulario()

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

        if (configuracao.hasIndicadorVisivel()) {
            isErro = false
            setOnClickListener { exibirAlerta() }
        }
    }

    fun setErro(msg: String) {
        if (msg.isNotEmpty() && configuracao.exibirIndicadorErro) {
            isErro = true
            mensagem = msg
            show()
            setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_erro))
        }
    }

    fun setInformacao(msg: String) {
        if (msg.isNotEmpty() && configuracao.exibirIndicadorInformacao) {
            isErro = false
            mensagem = msg
            show()
            setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_informacao))
        }
    }

    private fun exibirAlerta() {
        if (mensagem.isNotEmpty()) {
            AlertDialog.Builder(context)
                    .setTitle(context.getString(if (isErro) R.string.erro else R.string.informacao))
                    .setMessage(mensagem)
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