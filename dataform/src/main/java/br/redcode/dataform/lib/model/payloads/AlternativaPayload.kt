package br.redcode.dataform.lib.model.payloads

import java.io.Serializable

/**
 * Created by pedrofsn on 03/11/2017.
 */
data class AlternativaPayload(
        val id: Int,
        var selecionado: Boolean = false
) : Serializable