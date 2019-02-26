package br.redcode.dataform.lib.model

import java.util.*

/*
    CREATED BY @PEDROFSN
*/

data class FormAnswered(
        val id: Long,
        val idForm: Long,
        val lastUpdate: Date
)