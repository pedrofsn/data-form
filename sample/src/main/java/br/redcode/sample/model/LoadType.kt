package br.redcode.sample.model

import androidx.annotation.IntDef
import br.redcode.sample.model.LoadType.Companion.FORM_FROM_DATABASE
import br.redcode.sample.model.LoadType.Companion.FORM_WITH_ANSWERS_FROM_DATABASE
import br.redcode.sample.model.LoadType.Companion.JSON

@IntDef(value = [JSON, FORM_WITH_ANSWERS_FROM_DATABASE, FORM_FROM_DATABASE])
annotation class LoadType {

    companion object {
        const val JSON = 0
        const val FORM_WITH_ANSWERS_FROM_DATABASE = 1
        const val FORM_FROM_DATABASE = 2
    }
}