package br.redcode.dataform.lib.domain.handlers

import br.redcode.dataform.lib.interfaces.DuasLinhas

/**
 * Created by pedrofsn on 13/11/2017.
 */
abstract class HandlerInputPopup {

    var idPergunta = -1

    open fun chamarPopup(idPergunta: Int, functionAdicionarItem: (idPergunta: Int, duasLinhas: DuasLinhas) -> Unit) {
        this.idPergunta = idPergunta
    }

}