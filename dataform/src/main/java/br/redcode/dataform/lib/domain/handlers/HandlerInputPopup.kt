package br.redcode.dataform.lib.domain.handlers

import br.com.redcode.spinnable.library.model.Spinnable

/**
 * Created by pedrofsn on 13/11/2017.
 */
abstract class HandlerInputPopup {

    var idPergunta = -1

    open fun chamarPopup(idPergunta: Int, functionAdicionarItem: (idPergunta: Int, spinnable: Spinnable) -> Unit) {
        this.idPergunta = idPergunta
    }

}