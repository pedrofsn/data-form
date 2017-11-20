package br.redcode.dataform.lib.model.payloads

import java.io.Serializable

/**
 * Created by pedrofsn on 03/11/2017.
 */
data class AlternativaPayload(
        val id: Long,
        var selecionado: Boolean = false
) : Serializable