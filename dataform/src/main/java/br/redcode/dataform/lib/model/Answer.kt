package br.redcode.dataform.lib.model

import android.os.Parcelable
import br.com.redcode.spinnable.library.model.Spinnable
import kotlinx.android.parcel.Parcelize

/**
 * Created by pedrofsn on 31/10/2017.
 */
@Parcelize
data class Answer(
        var id: Int? = null,
        var answer: String? = null,
        var options: ArrayList<Spinnable>? = null,
        var images: ArrayList<Image>? = null,
        var tag: String? = null
) : Parcelable {

    fun hasText() = answer != null && answer?.isNotEmpty() ?: false
    fun hasOptions() = options != null && options?.isNotEmpty() ?: false
    fun hasOnlyOneOption() = hasOptions() && options!!.size == 1
    fun hasImages() = images != null && images?.isNotEmpty() ?: false

    // TODO: testar, caso o limite mínimo seja 0, a pergunta seja obrigatória. Provavelmente será necessário remover este isNotEmpty()
    fun hasAnswer() = id != null && (hasText() || hasOptions() || hasImages())

}

