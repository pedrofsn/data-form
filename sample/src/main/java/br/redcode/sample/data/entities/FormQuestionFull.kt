package br.redcode.sample.data.entities

data class FormQuestionFull(
        val settings: EntityFormSettings,
        val answers: List<AnswerFull>,
        val questions: List<EntityQuestionFull>
)