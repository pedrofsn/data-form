package br.redcode.dataform.lib.model

import android.content.Context
import android.os.Parcelable
import br.redcode.dataform.lib.R
import kotlinx.android.parcel.Parcelize

/**
 * Created by pedrofsn on 02/11/2017.
 */
@Parcelize
data class Limit(
    val min: Int,
    val max: Int,

    val auto: Boolean? = null
) : Parcelable {

    fun getMessageDefault(context: Context) =
        String.format(context.getString(R.string.error_limits), min, max)
}