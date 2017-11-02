package br.redcode.dataform.lib.model

import br.redcode.dataform.lib.utils.Constantes
import java.io.Serializable

/**
 * Created by pedrofsn on 31/10/2017.
 */
data class Pergunta(
        val id: Int,
        val descricao: String,
        val formato: String,
        var resposta: Resposta? = Resposta(),

        var limite: Limite? = null,
        var alternativas: ArrayList<Alternativa>? = null
) : Serializable {

    fun getLimiteMaximo(): Int {
        return limite?.maximo ?: 1
    }

    fun getLimiteMinimo(): Int {
        return limite?.minimo ?: 0
    }

    fun getQuantidadeImagens(): Int {
        return resposta?.imagens?.size ?: 0
    }

    fun isDentroDoLimiteMaximo(): Boolean {
        return getQuantidadeImagens() <= getLimiteMaximo()
    }

    fun isDentroDoLimiteMinimo(): Boolean {
        return getQuantidadeImagens() >= getLimiteMinimo()
    }

    fun isPerguntaTextual(): Boolean {
        return Constantes.TIPO_PERGUNTA_TEXTUAL == formato
    }

    fun isPerguntaObjetiva(): Boolean {
        return Constantes.TIPO_PERGUNTA_OBJETIVA == formato
    }

    fun isPerguntaMultiplaEscolha(): Boolean {
        return Constantes.TIPO_PERGUNTA_MULTIPLA_ESCOLHA == formato
    }

    fun isPerguntaImagem(): Boolean {
        return Constantes.TIPO_PERGUNTA_IMAGEM == formato && limite != null
    }

}