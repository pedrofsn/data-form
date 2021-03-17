package br.redcode.dataform.lib.model.payloads

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

/**
 * Created by pedrofsn on 03/11/2017.
 */
@Parcelize
data class PayloadAnswer(
    val id: Long,
    val resposta: @RawValue Any
) : Parcelable