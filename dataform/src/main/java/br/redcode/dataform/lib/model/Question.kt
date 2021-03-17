package br.redcode.dataform.lib.model

import android.os.Parcelable
import br.com.redcode.spinnable.library.model.Spinnable
import br.redcode.dataform.lib.utils.Constants.EMPTY_STRING
import br.redcode.dataform.lib.utils.Constants.QUESTION_DESCRIPTION_SYMBOL_REQUIRED
import br.redcode.dataform.lib.utils.Constants.TYPE_QUESTION_IMAGE_CAMERA_OR_GALLERY
import br.redcode.dataform.lib.utils.Constants.TYPE_QUESTION_IMAGE_ONLY_CAMERA
import br.redcode.dataform.lib.utils.Constants.TYPE_QUESTION_IMAGE_ONLY_GALLERY
import br.redcode.dataform.lib.utils.Constants.TYPE_QUESTION_INFORMATIVE_TEXT
import br.redcode.dataform.lib.utils.Constants.TYPE_QUESTION_MULTIPLE_CHOICE
import br.redcode.dataform.lib.utils.Constants.TYPE_QUESTION_OBJECTIVE_LIST
import br.redcode.dataform.lib.utils.Constants.TYPE_QUESTION_OBJECTIVE_SPINNER
import br.redcode.dataform.lib.utils.Constants.TYPE_QUESTION_PERCENTAGE
import br.redcode.dataform.lib.utils.Constants.TYPE_QUESTION_TEXTUAL
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

/**
 * Created by pedrofsn on 31/10/2017.
 */
@Parcelize
data class Question(
    val id: Long,
    val description: String,
    val type: String,
    val information: String? = EMPTY_STRING,
    val required: Boolean = true,
    val format: String? = null,

    var limit: Limit? = null,
    var options: ArrayList<Spinnable>? = null,
    var customSettings: HashMap<String, Boolean>? = null,
    var extra: @RawValue Any? = null
) : Parcelable {

    fun getDescriptionWithSymbolRequired() =
        if (required) "$description $QUESTION_DESCRIPTION_SYMBOL_REQUIRED" else description

    fun getLimitMax() = limit?.max ?: 1
    fun getLimitMin(): Int = limit?.min ?: 0
    fun isQuestionInformativeText() = TYPE_QUESTION_INFORMATIVE_TEXT == type
    fun isQuestionTextual() = TYPE_QUESTION_TEXTUAL == type
    fun isQuestionObjectiveList() = TYPE_QUESTION_OBJECTIVE_LIST == type
    fun isQuestionObjectiveSpinner() = TYPE_QUESTION_OBJECTIVE_SPINNER == type
    fun isQuestionPercentage() = TYPE_QUESTION_PERCENTAGE == type
    fun isQuestionMultipleChoice() = validateLimits(TYPE_QUESTION_MULTIPLE_CHOICE == type)
    fun isQuestionImageOnlyCamera() = validateLimits(TYPE_QUESTION_IMAGE_ONLY_CAMERA == type)
    fun isQuestionImageOnlyGallery() = validateLimits(TYPE_QUESTION_IMAGE_ONLY_GALLERY == type)
    fun isQuestionImageCameraOrGallery() =
        validateLimits(TYPE_QUESTION_IMAGE_CAMERA_OR_GALLERY == type)

    fun hasOptions() = options?.isNotEmpty() == true
    fun countOptions() = options?.size ?: 0

    private fun validateLimits(result: Boolean): Boolean {
        if (result) crashAppWithoutLimitInformation()
        return result
    }

    private fun crashAppWithoutLimitInformation() {
        when {
            limit?.auto == true -> {
                limit = Limit(
                    max = countOptions(),
                    min = getLimitMin(),
                    auto = true
                )
            }
            hasOptions() && getLimitMax() > countOptions() -> throw RuntimeException("EN: Limit max is grather than options quantity in question ${id}\nPT: O limite máximo é maior que a quantidade de alternativas da pergunta ${id}")
            getLimitMin() < 0 -> throw RuntimeException("EN: Negative min limit in question ${id}\nPT: O limite mínimo está negativo na pergunta ${id}")
            getLimitMin() > getLimitMax() -> throw RuntimeException("EN: Negative min limit in question ${id}\nPT: O limite mínimo está negativo na pergunta ${id}")
            limit == null -> throw RuntimeException("EN: Did you forgot limits in JSON question ${id}\nPT: Falta especificar os limites no JSON da pergunta ${id}")
        }
    }

    fun hasInformation() = information?.isNotEmpty() == true
}