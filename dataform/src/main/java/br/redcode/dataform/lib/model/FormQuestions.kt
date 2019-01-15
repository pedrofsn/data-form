package br.redcode.dataform.lib.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by pedrofsn on 12/11/2017.
 */
@Parcelize
data class FormQuestions(
        val settings: QuestionSettings,
        val questions: ArrayList<Question>
) : Parcelable