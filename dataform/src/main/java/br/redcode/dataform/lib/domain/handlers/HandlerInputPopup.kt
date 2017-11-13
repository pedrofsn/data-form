package br.redcode.dataform.lib.domain.handlers

import br.redcode.dataform.lib.interfaces.DuasLinhas

/**
 * Created by pedrofsn on 13/11/2017.
 */
abstract class HandlerInputPopup {

    lateinit var function: (duasLinhas: DuasLinhas) -> Unit

    abstract fun chamarPopup()

    fun adicionarElementoNaLista(duasLinhas: DuasLinhas) {
        function.invoke(duasLinhas)
    }

}