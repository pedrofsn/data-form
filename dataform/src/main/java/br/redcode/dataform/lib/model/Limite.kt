package br.redcode.dataform.lib.model

import android.content.Context
import br.redcode.dataform.lib.R
import java.io.Serializable

/**
 * Created by pedrofsn on 02/11/2017.
 */
data class Limite(val minimo: Int, val maximo: Int) : Serializable {

    fun getMensagemDefault(context: Context): String {
        return String.format(context.getString(R.string.mensagem_limites_default), minimo, maximo)
    }

}