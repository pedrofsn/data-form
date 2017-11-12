package br.redcode.dataform.lib.model

import java.io.Serializable

data class ConfiguracaoFormulario(
        val exibirIndicadorErro: Boolean = true,
        val exibirIndicadorInformacao: Boolean = true
) : Serializable {

    fun hasIndicadorVisivel(): Boolean {
        return exibirIndicadorErro or exibirIndicadorInformacao
    }

}