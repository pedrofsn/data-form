package br.redcode.sample.data.entities

import androidx.room.Embedded
import androidx.room.Relation

data class EntityQuestionFull(

        @Embedded
        val question: EntityQuestion,

        @Embedded
        val limit: EntityQuestionLimit?,

        @Relation(parentColumn = "question_id", entityColumn = "question_id")
        val options: List<EntityQuestionOption>? = null,

        @Relation(parentColumn = "question_id", entityColumn = "question_id")
        val customSettings: List<EntityQuestionCustomSettings>? = null

)