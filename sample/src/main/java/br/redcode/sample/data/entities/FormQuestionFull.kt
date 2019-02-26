package br.redcode.sample.data.entities

import java.util.*

data class FormQuestionFull(
        val idForm: Long,
        val lastUpdate: Date? = Date(),
        val settings: EntityFormSettings,
        val answers: List<AnswerFull>,
        val questions: List<EntityQuestionFull>
)