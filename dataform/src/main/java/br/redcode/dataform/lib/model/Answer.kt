package br.redcode.dataform.lib.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by pedrofsn on 31/10/2017.
 */
@Parcelize
data class Answer(
        var id: Long? = null,
        var idQuestion: Long,
        var percentage: Int? = null,
        var text: String? = null,
        var options: List<String>? = null,
        var images: List<Image>? = null,
        var tag: String? = null
) : Parcelable {

    fun hasText() = text != null && text?.isNotEmpty() ?: false
    fun hasOptions() = options != null && options?.isNotEmpty() ?: false
    fun hasOnlyOneOption() = hasOptions() && options!!.size == 1
    fun hasImages() = images != null && images?.isNotEmpty() ?: false

    fun hasAnswer() = id != null && (hasText() || hasOptions() || hasImages())

}