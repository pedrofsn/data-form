package br.redcode.dataform.lib.model

import android.os.Parcelable
import java.util.*
import kotlinx.android.parcel.Parcelize

/**
 * Created by pedrofsn on 12/11/2017.
 */
@Parcelize
data class Form(
        val idForm: Long = -1,
        val lastUpdate: Date? = Date(),
        val settings: FormSettings,
        val answers: List<Answer>,
        val questions: ArrayList<Question>
) : Parcelable