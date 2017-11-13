package br.redcode.dataform.lib.model

import br.redcode.dataform.lib.interfaces.DuasLinhas

/**
 * Created by pedrofsn on 13/11/2017.
 */
data class ItemRemovivel(val titulo: String, val subtitulo: String) : DuasLinhas {

    override fun getLinha1(): String {
        return titulo
    }

    override fun getLinha2(): String {
        return subtitulo
    }
}