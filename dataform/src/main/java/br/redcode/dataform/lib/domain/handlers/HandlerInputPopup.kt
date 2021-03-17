package br.redcode.dataform.lib.domain.handlers

import br.com.redcode.spinnable.library.model.Spinnable
import br.redcode.dataform.lib.utils.Constants.INVALID_VALUE

/**
 * Created by pedrofsn on 13/11/2017.
 */
abstract class HandlerInputPopup {

    var idQuestion = INVALID_VALUE.toLong()

    open fun chamarPopup(
        idQuestion: Long,
        functionAdicionarItem: (idQuestion: Long, spinnable: Spinnable) -> Unit
    ) {
        this.idQuestion = idQuestion
    }
}