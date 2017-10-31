package br.redcode.dataform.lib.interfaces

import java.io.Serializable

/**
 * Created by pedrofsn on 31/10/2017.
 */
interface Spinnable : Serializable {

    fun getId(): Int

    fun getTexto(): String

}