package br.redcode.dataform.lib.model

import br.redcode.dataform.lib.utils.Constantes
import java.io.Serializable

/**
 * Created by pedrofsn on 13/11/2017.
 */
data class Campo(
        val tag: String,
        val descricao: String,
        val tipo: String,

        val alternativas: List<Alternativa>? = null
) : Serializable {

    fun isTextoData(): Boolean {
        return Constantes.INPUT_TEXTO_DATA == tipo
    }

    fun isSpinner(): Boolean {
        return Constantes.INPUT_SPINNER == tipo
    }

}