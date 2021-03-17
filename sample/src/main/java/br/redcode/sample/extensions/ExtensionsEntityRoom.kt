package br.redcode.sample.extensions

import br.com.redcode.spinnable.library.model.Spinnable
import br.redcode.dataform.lib.model.Answer
import br.redcode.dataform.lib.model.FormSettings
import br.redcode.dataform.lib.model.Image
import br.redcode.dataform.lib.model.Limit
import br.redcode.dataform.lib.model.Question
import br.redcode.sample.data.entities.EntityAnswer
import br.redcode.sample.data.entities.EntityAnswerImage
import br.redcode.sample.data.entities.EntityAnswerOption
import br.redcode.sample.data.entities.EntityFormSettings
import br.redcode.sample.data.entities.EntityQuestion
import br.redcode.sample.data.entities.EntityQuestionCustomSettings
import br.redcode.sample.data.entities.EntityQuestionLimit
import br.redcode.sample.data.entities.EntityQuestionOption
import java.util.*

fun FormSettings.toEntity(idForm: Long) = EntityFormSettings(
    idForm = idForm,
    showIndicatorError = showIndicatorError,
    showIndicatorInformation = showIndicatorInformation,
    showSymbolRequired = showSymbolRequired,
    editable = editable,
    backgroundColor = backgroundColor
)

// -----------> QUESTIONS
fun Question.toEntity(idForm: Long) = EntityQuestion(
    idQuestion = id,
    idForm = idForm,
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
fun List<Spinnable>?.fromOptionsToEntity(idQuestion: Long, idForm: Long) =
    this?.map { it.toEntity(idQuestion = idQuestion, idForm = idForm) }
        ?: emptyList()

fun Spinnable.toEntity(idQuestion: Long, idForm: Long) = EntityQuestionOption(
    idOption = id,
    description = description,
    selected = selected,
    idQuestion = idQuestion,
    idForm = idForm
)

// CUSTOM SETTINGS
fun HashMap<String, Boolean>.toEntity(
    idQuestion: Long,
    idForm: Long
): List<EntityQuestionCustomSettings> {
    val list = arrayListOf<EntityQuestionCustomSettings>()

    if (isNullOrEmpty().not()) {
        for (pair in this) {
            val key = pair.key
            val value = pair.value

            if (key.isNotBlank()) {
                val entity = EntityQuestionCustomSettings(
                    idQuestion = idQuestion,
                    key = key,
                    value = value,
                    idForm = idForm
                )

                list.add(entity)
            }
        }
    }

    return list
}

// -----------> ANSWERS
fun Answer.toEntity(idQuestion: Long, form_with_answers_id: Long) = EntityAnswer(
    text = text,
    percentage = percentage,
    idQuestion = idQuestion,
    form_with_answers_id = form_with_answers_id
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

fun List<EntityQuestionOption>?.changeQuestionOptions(idQuestion: Long) =
    this?.map { obj -> obj.copy(idQuestion = idQuestion) }
        ?: emptyList()

fun List<EntityQuestionCustomSettings>?.changeQuestionCustomSettings(idQuestion: Long) =
    this?.map { obj -> obj.copy(idQuestion = idQuestion) }
        ?: emptyList()

fun List<EntityAnswerImage>?.toModel() = this?.map { obj -> obj.toModel() }
    ?: emptyList()