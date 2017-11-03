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
        val informacao: String? = Constantes.STRING_VAZIA,

        var limite: Limite? = null,
        var alternativas: ArrayList<Alternativa>? = null
) : Serializable {

    fun isPerguntaTextual(): Boolean {
        return Constantes.TIPO_PERGUNTA_TEXTUAL == formato
    }

    fun isPerguntaObjetivaLista(): Boolean {
        return Constantes.TIPO_PERGUNTA_OBJETIVA_LISTA == formato
    }

    fun isPerguntaObjetivaSpinner(): Boolean {
        return Constantes.TIPO_PERGUNTA_OBJETIVA_SPINNER == formato
    }

    fun isPerguntaMultiplaEscolha(): Boolean {
        return Constantes.TIPO_PERGUNTA_MULTIPLA_ESCOLHA == formato
    }

    fun isPerguntaImagemSomenteCamera(): Boolean {
        if (limite == null) {
            throw RuntimeException("Falta especificar os limites no JSON")
        }
        return Constantes.TIPO_PERGUNTA_IMAGEM_SOMENTE_CAMERA == formato
    }

    fun isPerguntaImagemSomenteGaleria(): Boolean {
        if (limite == null) {
            throw RuntimeException("Falta especificar os limites no JSON")
        }
        return Constantes.TIPO_PERGUNTA_IMAGEM_SOMENTE_GALERIA == formato
    }

    fun isPerguntaImagemCameraOuGaleria(): Boolean {
        if (limite == null) {
            throw RuntimeException("Falta especificar os limites no JSON")
        }
        return Constantes.TIPO_PERGUNTA_IMAGEM_CAMERA_OU_GALERIA == formato
    }

}