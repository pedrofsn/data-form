package br.redcode.sample.data.entities

import androidx.room.Embedded
import androidx.room.Relation

data class EntityAnswerFull(

        @Embedded
        val answer: EntityAnswer,

        @Relation(parentColumn = "answer_id", entityColumn = "answer_id")
        val options: List<EntityAnswerOption>? = null,

        @Relation(parentColumn = "answer_id", entityColumn = "answer_id")
        val images: List<EntityAnswerImage>? = null

)