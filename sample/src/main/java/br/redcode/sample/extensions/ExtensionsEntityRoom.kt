package br.redcode.sample.extensions

import br.com.redcode.spinnable.library.model.Spinnable
import br.redcode.dataform.lib.model.*
import br.redcode.sample.data.entities.*

// -----------> FORM
fun FormQuestions.toEntity(): FormQuestionFull {
    val entityFormSettings = settings.toEntity()
    val listEntityQuestionsFull = arrayListOf<EntityQuestionFull>()
    val awnserFull = arrayListOf<AnswerFull>()

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

            val answerIdsConfirmed = arrayListOf<String>()

            // TODO improve
            answer.options?.let { ids ->
                if (ids.isNotEmpty()) {
                    val options = entityOptionsQuestion?.filter { it.idOption in ids }?.map { it.idOption }
                            ?: emptyList()
                    //?.map { it.toEntity(idAnswer = currentIdAnwser) }?.let { entityAnswersOptions.addAll(it) }
                    answerIdsConfirmed.addAll(options)
                }
            }

            val answerFull = AnswerFull(
                    answer = entityAnswer,
                    options = answerIdsConfirmed,
                    images = answer.images
            )

            awnserFull.add(answerFull)
        }

        val entityQuestionFull = EntityQuestionFull(
                question = entityQuestion,
                limit = entityLimit,
                options = entityOptionsQuestion,
                customSettings = entityCustomSettings
        )

        listEntityQuestionsFull.add(entityQuestionFull)
    }

    return FormQuestionFull(
            settings = entityFormSettings,
            answers = awnserFull,
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

fun EntityQuestionOption.toEntityAnswerQuestion(idAnswer: Long) = EntityAnswerOption(
        idAnswer = idAnswer,
        idQuestionOption = idQuestionOption
)

// ----------------> EASY

fun List<EntityQuestionOption>?.changeQuestionOptions(idQuestion: Long) = this?.map { obj -> obj.copy(idQuestion = idQuestion) }
        ?: emptyList()

fun List<EntityQuestionCustomSettings>?.changeQuestionCustomSettings(idQuestion: Long) = this?.map { obj -> obj.copy(idQuestion = idQuestion) }
        ?: emptyList()

fun List<EntityAnswerImage>?.changeAnswerImage(idAnswer: Long) = this?.map { obj -> obj.copy(idAnswer = idAnswer) }
        ?: emptyList()

fun List<EntityAnswerOption>?.changeAnswerOption(idAnswer: Long) = this?.map { obj -> obj.copy(idAnswer = idAnswer) }
        ?: emptyList()