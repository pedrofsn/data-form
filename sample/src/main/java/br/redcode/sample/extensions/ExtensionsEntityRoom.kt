package br.redcode.sample.extensions

import br.com.redcode.spinnable.library.model.Spinnable
import br.redcode.dataform.lib.model.*
import br.redcode.sample.data.entities.*

// -----------> FORM
fun FormQuestions.toEntity(): EntityFormQuestionFull {
    val entityFormSettings = settings.toEntity()
    val listEntityQuestionsFull = arrayListOf<EntityQuestionFull>()
    val listEntityAwnserFull = arrayListOf<EntityAnswerFull>()

    questions.forEach { question ->
        val entityQuestion = question.toEntity()
        val currentIdQuestion = question.id

        val entityLimit = question.limit?.toEntity(idQuestion = currentIdQuestion)
        val entityOptionsQuestion = question.options?.fromOptionsToEntity(idQuestion = currentIdQuestion)
        val entityCustomSettings = question.customSettings?.toEntity(idQuestion = currentIdQuestion)
        // TODO and extra @RawValue?


        val answer = answers.firstOrNull { currentIdQuestion == it.idQuestion }
        if (answer != null) {
            val entityAnswer = answer.toEntity(idQuestion = currentIdQuestion)
            val currentIdAnwser = entityAnswer.idAnswer

            val entityAnswerImages = answer.images?.toEntity(idAnswer = currentIdAnwser)
            val entityAnswersOptions = arrayListOf<EntityAnswerOption>()

            // TODO improve
            answer.options?.let { ids ->
                if (ids.isNotEmpty()) {
                    entityOptionsQuestion?.filter { it.idOption in ids }?.map { it.toEntity() }?.let { entityAnswersOptions.addAll(it) }
                }
            }

            val entityAnswerFull = EntityAnswerFull(
                    answer = entityAnswer,
                    options = entityAnswersOptions,
                    images = entityAnswerImages
            )

            listEntityAwnserFull.add(entityAnswerFull)
        }

        val entityQuestionFull = EntityQuestionFull(
                question = entityQuestion,
                limit = entityLimit,
                options = entityOptionsQuestion,
                customSettings = entityCustomSettings
        )

        listEntityQuestionsFull.add(entityQuestionFull)


    }

    return EntityFormQuestionFull(
            settings = entityFormSettings,
            answers = listEntityAwnserFull,
            questions = listEntityQuestionsFull
    )
}

fun FormSettings.toEntity() = EntityFormSettings(
        showIndicatorError = showIndicatorError,
        showIndicatorInformation = showIndicatorInformation,
        showSymbolRequired = showSymbolRequired,
        editable = editable,
        backgroundColor = backgroundColor
)

// -----------> QUESTIONS
fun Question.toEntity() = EntityQuestion(
        id = id,
        description = description,
        type = type,
        information = information,
        required = required,
        format = format
)

// LIMIT
fun Limit.toEntity(idQuestion: Long) = EntityQuestionLimit(
        min = min,
        max = max,
        idQuestion = idQuestion
)

// OPTIONS
fun List<Spinnable>?.fromOptionsToEntity(idQuestion: Long) = this?.map { it.toEntity(idQuestion = idQuestion) }
        ?: emptyList()

fun Spinnable.toEntity(idQuestion: Long) = EntityQuestionOption(
        idOption = id,
        description = description,
        selected = selected,
        idQuestion = idQuestion
)

// CUSTOM SETTINGS
fun HashMap<String, Boolean>.toEntity(idQuestion: Long): List<EntityQuestionCustomSettings> {
    val list = arrayListOf<EntityQuestionCustomSettings>()

    if (isNullOrEmpty().not()) {
        for (pair in this) {
            val key = pair.key
            val value = pair.value

            if (key.isNotBlank()) {
                val entity = EntityQuestionCustomSettings(
                        idQuestion = idQuestion,
                        key = key,
                        value = value
                )

                list.add(entity)
            }
        }
    }

    return list
}

// -----------> ANSWERS
fun Answer.toEntity(idQuestion: Long) = EntityAnswer(
        text = text,
        percentage = percentage,
        idQuestion = idQuestion
)

fun List<Image>?.toEntity(idAnswer: Long) = this?.map { it.toEntity(idAnswer = idAnswer) }
        ?: emptyList()

fun Image.toEntity(idAnswer: Long) = EntityAnswerImage(
        idAnswer = idAnswer,
        image = image,
        subtitle = subtitle
)

fun EntityQuestionOption.toEntity() = EntityAnswerOption(
        idQuestionOption = idQuestionOption
)