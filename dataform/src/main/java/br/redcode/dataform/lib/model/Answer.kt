package br.redcode.dataform.lib.model

import android.os.Parcelable
import br.redcode.dataform.lib.utils.Constants.INVALID_VALUE
import kotlinx.android.parcel.Parcelize

/**
 * Created by pedrofsn on 31/10/2017.
 */
@Parcelize
data class Answer(
        var id: Long? = null,
        var idQuestion: Long,

        var text: String? = null,
        var percentage: Int? = null,
        var options: List<String>? = null,
        var images: List<Image>? = null
) : Parcelable {

    fun hasText() = text != null && text?.isNotEmpty() ?: false
    fun hasPercentage() = percentage != null && percentage!! >= 0 && percentage!! <= 100
    fun hasOptions() = options != null && options?.isNotEmpty() ?: false
    fun hasOnlyOneOption() = hasOptions() && options!!.size == 1
    fun hasImages() = images != null && images?.isNotEmpty() ?: false

    fun hasAnswer() = idQuestion != INVALID_VALUE.toLong() &&
            (hasText() || hasPercentage() || hasOptions() || hasImages())

}