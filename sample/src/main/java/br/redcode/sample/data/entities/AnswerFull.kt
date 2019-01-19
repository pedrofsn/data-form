package br.redcode.sample.data.entities

import br.redcode.dataform.lib.model.Image

data class AnswerFull(
        val answer: EntityAnswer,
        val options: List<String>? = null,
        val images: List<Image>? = null
//        @Relation(parentColumn = "answer_id", entityColumn = "answer_id")
)