package br.redcode.sample.data.entities

import androidx.room.Embedded
import br.redcode.dataform.lib.model.Image

data class EntityAnswerFull(

        @Embedded
        val answer: EntityAnswer,

        val options: List<String>? = null,

//        @Relation(parentColumn = "answer_id", entityColumn = "answer_id")
        val images: List<Image>? = null

)