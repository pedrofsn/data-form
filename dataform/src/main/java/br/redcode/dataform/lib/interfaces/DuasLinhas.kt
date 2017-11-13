package br.redcode.dataform.lib.interfaces

import java.io.Serializable

/**
 * Created by pedrofsn on 13/11/2017.
 */
interface DuasLinhas : Serializable {

    fun getLinha1(): String

    fun getLinha2(): String

}