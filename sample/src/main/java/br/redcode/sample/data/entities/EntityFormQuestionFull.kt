package br.redcode.sample.data.entities

data class EntityFormQuestionFull(
        val settings: EntityFormSettings,
        val answers: List<EntityAnswerFull>,
        val questions: List<EntityQuestionFull>
)