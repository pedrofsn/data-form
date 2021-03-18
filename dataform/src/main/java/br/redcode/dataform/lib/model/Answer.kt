package br.redcode.dataform.lib.model

import android.os.Parcelable
import br.redcode.dataform.lib.utils.Constants.INVALID_VALUE
import kotlinx.android.parcel.Parcelize

private const val STRING_IMAGES = "%d imagens"
private const val STRING_IMAGE = "%d imagem"

/**
 * Created by pedrofsn on 31/10/2017.
 */
@Parcelize
data class Answer(
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

    fun hasAnswer() = (hasText() || hasPercentage() || hasOptions() || hasImages())
        && isValidIdQuestion()

    private fun isValidIdQuestion() = idQuestion != INVALID_VALUE.toLong()

    fun getPreviewAnswer(question: Question): String? = when {
        hasText() -> text
        hasPercentage() -> percentage.toString()
        hasOptions() || hasOnlyOneOption() -> getPreviewAnswerForOptions(question)
        hasImages() -> getPreviewAnswerImage()
        else -> ""
    }

    private fun getPreviewAnswerForOptions(question: Question) = question.options
        ?.filter { it.id in (options ?: emptyList()) }
        ?.joinToString(
            separator = ", ",
            prefix = "",
            postfix = "",
            limit = 3,
            truncated = "..."
        ) { it.description }

    private fun getPreviewAnswerImage(): String {
        val count = images?.count() ?: 0

        // TODO --> I'm less shameless for moved magic string to constants #needmovefast
        return when {
            count > 1 -> String.format(STRING_IMAGES, count)
            else -> String.format(STRING_IMAGE, count)
        }
//        images?.joinToString(separator = ", ", prefix = "", postfix = "", limit = 3, truncated = "...") { it.image }
    }
}