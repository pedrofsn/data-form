package br.redcode.dataform.lib.interfaces

/**
 * Created by pedrofsn on 13/11/2017.
 */
open class DuasLinhas(val linha1: String, val linha2: String, val auxiliar: Any? = null) : Spinnable {
    override fun getId() = linha1
    override fun getTexto() = linha2
}