package br.redcode.dataform.lib.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

/**
 * Created by pedrofsn on 12/11/2017.
 */
@Parcelize
data class Form(
        val idForm: Long,
        val lastUpdate: Date,
        val settings: FormSettings,
        val answers: List<Answer>,
        val questions: ArrayList<Question>
) : Parcelable