package br.redcode.dataform.lib.model

import br.redcode.dataform.lib.interfaces.DuasLinhas
import java.io.Serializable

/**
 * Created by pedrofsn on 31/10/2017.
 */
data class Resposta(
        var idPergunta: Int? = null,
        var resposta: String? = null,

        var respostas: List<DuasLinhas>? = null,
        var alternativa: Alternativa? = null,
        var alternativas: List<Alternativa>? = null,
        var imagens: List<Imagem>? = null
) : Serializable {

    fun hasResposta(): Boolean {
        return (idPergunta != null
                || resposta != null && resposta?.isNotEmpty() ?: false
                || alternativa != null
                || alternativas != null && alternativas?.isNotEmpty() ?: false // TODO: testar, caso o limite mínimo seja 0, a pergunta seja obrigatória. Provavelmente será necessário remover este isNotEmpty()
                || imagens != null && imagens?.isNotEmpty() ?: false)

                || respostas != null
    }

}

