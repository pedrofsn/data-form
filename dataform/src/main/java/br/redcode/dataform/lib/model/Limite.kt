package br.redcode.dataform.lib.model

import android.content.Context
import br.redcode.dataform.lib.R

/**
 * Created by pedrofsn on 02/11/2017.
 */
data class Limite(val minimo: Int, val maximo: Int) {

    fun getMensagemDefault(context: Context): String {
        return String.format(context.getString(R.string.mensagem_limites_default), minimo, maximo)
    }

}