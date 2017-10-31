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
        var resposta: Resposta = Resposta()
) : Serializable {

    fun isPerguntaTextual(): Boolean {
        return Constantes.TIPO_PERGUNTA_TEXTUAL == formato
    }

}