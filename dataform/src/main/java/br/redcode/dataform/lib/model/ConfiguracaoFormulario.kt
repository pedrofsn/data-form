package br.redcode.dataform.lib.model

import java.io.Serializable

data class ConfiguracaoFormulario(
        val exibirIndicadorErro: Boolean = true,
        val exibirIndicadorInformacao: Boolean = true,
        val editavel: Boolean = true,
        val corBackgroundFormulario: String = "#ffffff"
) : Serializable {

    fun hasIndicadorVisivel(): Boolean {
        return exibirIndicadorErro or exibirIndicadorInformacao
    }

}