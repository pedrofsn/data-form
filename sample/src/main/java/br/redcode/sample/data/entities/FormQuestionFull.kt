package br.redcode.sample.data.entities

import androidx.room.Embedded
import androidx.room.Relation
import java.util.*

data class FormQuestionFull(
        val idForm: Long,
        val lastUpdate: Date? = Date(),
        val settings: EntityFormSettings,
        val answers: List<AnswerFull>,
        val questions: List<EntityQuestionFull>
)

data class FormQuestionFull2(

        @Embedded
        val form: EntityForm,

//        @Embedded
//        val settings: EntityFormSettings?,
//
//        @Relation(parentColumn = "answer_id", entityColumn = "answer_id")
//        val answers: List<AnswerFull>,

        @Relation(parentColumn = "form_id", entityColumn = "form_id")
        val questions: List<EntityQuestionFull>
)