package br.redcode.dataform.lib.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class QuestionSettings(
        val showIndicatorError: Boolean = true,
        val showIndicatorInformation: Boolean = true,
        val editable: Boolean = true,
        val backgroundColor: String? = null
) : Parcelable {

    fun hasIndicator() = showIndicatorError or showIndicatorInformation

}