package br.redcode.dataform.lib.interfaces

import br.redcode.dataform.lib.model.Answer

/**
 * Created by pedrofsn on 31/10/2017.
 */
interface Questionable {

    fun fillAnswer(answer: Answer)
    fun getAnswer(): Answer

    fun isFilledCorrect(): Boolean
    fun getMessageErrorFill(): String
    fun getMessageInformation(): String
    fun showMessageForErrorFill(isFilledRight: Boolean)

}