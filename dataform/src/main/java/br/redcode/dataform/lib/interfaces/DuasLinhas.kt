package br.redcode.dataform.lib.interfaces

/**
 * Created by pedrofsn on 13/11/2017.
 */
data class DuasLinhas(val linha1: String, val linha2: String) : Spinnable {
    override fun getId() = linha1
    override fun getTexto() = linha2
}