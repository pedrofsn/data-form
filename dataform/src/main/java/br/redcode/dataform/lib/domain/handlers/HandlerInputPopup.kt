package br.redcode.dataform.lib.domain.handlers

import br.com.redcode.spinnable.library.model.Spinnable
import br.redcode.dataform.lib.utils.Constants.INVALID_VALUE

/**
 * Created by pedrofsn on 13/11/2017.
 */
abstract class HandlerInputPopup {

    var idPergunta = INVALID_VALUE

    open fun chamarPopup(idPergunta: Int, functionAdicionarItem: (idPergunta: Int, spinnable: Spinnable) -> Unit) {
        this.idPergunta = idPergunta
    }

}