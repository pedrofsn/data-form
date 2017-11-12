package br.redcode.dataform.lib.model

import java.io.Serializable

data class ConfiguracaoFormulario(
        val exibirAlertaPreenchimentoIncorreto: Boolean = true,
        val exibirInformacaoSobreLimites: Boolean = true
) : Serializable