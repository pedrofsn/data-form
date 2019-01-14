package br.redcode.dataform.lib.interfaces

import br.redcode.dataform.lib.model.Resposta

/**
 * Created by pedrofsn on 31/10/2017.
 */
interface Questionable {

    fun getAnswer(): Resposta

    fun isFilledCorrect(): Boolean

    fun getMessageErrorFill(): String

    fun getMessageInformation(): String

    fun showMessageForErrorFill(isPreenchidoCorretamente: Boolean)

    fun isRequired(): Boolean

}