package br.redcode.dataform.lib.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by pedrofsn on 02/11/2017.
 */
@Parcelize
data class Image(
    val image: String,
    val subtitle: String? = null
) : Parcelable