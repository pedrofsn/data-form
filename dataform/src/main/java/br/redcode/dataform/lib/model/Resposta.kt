package br.redcode.dataform.lib.model

import java.io.Serializable

/**
 * Created by pedrofsn on 31/10/2017.
 */
data class Resposta(
        var resposta: String? = null,

        var alternativa: Alternativa? = null,
        var alternativas: List<Alternativa>? = null,
        var imagens: List<Imagem>? = null
) : Serializable