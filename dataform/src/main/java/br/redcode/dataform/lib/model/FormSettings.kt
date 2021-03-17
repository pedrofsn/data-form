package br.redcode.dataform.lib.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FormSettings(
    val inputAnswersInOtherScreen: Boolean = false,
    val showIndicatorError: Boolean = true,
    val showIndicatorInformation: Boolean = true,
    val showInformation: Boolean = true,
    val showSymbolRequired: Boolean = true,
    val editable: Boolean = true,
    val backgroundColor: String? = null,
    var idLayoutWrapper: Int? = null
) : Parcelable {

    fun hasIndicator() = showIndicatorError or showIndicatorInformation
}