package br.redcode.dataform.lib.model

import br.redcode.dataform.lib.interfaces.Spinnable
import br.redcode.dataform.lib.model.payloads.AlternativaPayload

/**
 * Created by pedrofsn on 31/10/2017.
 */
data class Alternativa(
        @get:JvmName("getIdString") val id: String,
        val descricao: String,
        var selecionado: Boolean = false
) : Spinnable {

    override fun getId() = id
    override fun getTexto(): String {
        return descricao
    }

    fun toDTO(): AlternativaPayload {
        return AlternativaPayload(id.toLong(), selecionado)
    }
}