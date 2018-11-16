package br.redcode.dataform.lib.model

import android.os.Parcelable
import br.com.redcode.spinnable.library.model.Spinnable
import kotlinx.android.parcel.Parcelize

/**
 * Created by pedrofsn on 31/10/2017.
 */
@Parcelize
data class Resposta(
        var idPergunta: Int? = null,
        var resposta: String? = null,
        var respostas: ArrayList<Spinnable>? = null,

        var alternativa: Spinnable? = null,
        var alternativas: ArrayList<Spinnable>? = null,
        var imagens: ArrayList<Imagem>? = null,
        var tag: String? = null
) : Parcelable {

    fun hasResposta() = respostas != null || (idPergunta != null
            || resposta != null && resposta?.isNotEmpty() ?: false
            || alternativa != null
            || alternativas != null && alternativas?.isNotEmpty() ?: false // TODO: testar, caso o limite mínimo seja 0, a pergunta seja obrigatória. Provavelmente será necessário remover este isNotEmpty()
            || imagens != null && imagens?.isNotEmpty() ?: false)

}

