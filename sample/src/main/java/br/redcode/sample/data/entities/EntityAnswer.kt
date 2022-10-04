package br.redcode.sample.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import br.redcode.dataform.lib.model.Answer
import br.redcode.dataform.lib.model.Image

@Entity(
    tableName = "answers",
    foreignKeys = [
        ForeignKey(
            entity = EntityQuestion::class,
            parentColumns = arrayOf("question_id"),
            childColumns = arrayOf("question_id"),
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = EntityFormAnswered::class,
            parentColumns = arrayOf("form_with_answers_id"),
            childColumns = arrayOf("form_with_answers_id"),
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(
            value = [
                "form_with_answers_id",
                "question_id"
            ],
            unique = true
        )
    ]
)
data class EntityAnswer(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "answer_id") val idAnswer: Long = 0,

    @ColumnInfo(name = "question_id", index = true) val idQuestion: Long,

    val text: String? = null,
    val percentage: Int? = null,

    val form_with_answers_id: Long
) {
    fun toModel(options: List<String>?, images: List<Image>?) = Answer(
        idQuestion = idQuestion,
        text = text,
        percentage = percentage,
        options = options,
        images = images
    )
}