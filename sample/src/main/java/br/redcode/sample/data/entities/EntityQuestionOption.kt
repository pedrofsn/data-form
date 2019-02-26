package br.redcode.sample.data.entities

import androidx.room.*
import br.com.redcode.spinnable.library.model.Spinnable

@Entity(
        tableName = "question_options",
        foreignKeys = [
            ForeignKey(
                    entity = EntityQuestion::class,
                    parentColumns = arrayOf("question_id"),
                    childColumns = arrayOf("question_id"),
                    onDelete = ForeignKey.CASCADE,
                    onUpdate = ForeignKey.CASCADE
            ),
            ForeignKey(
                    entity = EntityForm::class,
                    parentColumns = arrayOf("form_id"),
                    childColumns = arrayOf("form_id"),
                    onDelete = ForeignKey.CASCADE,
                    onUpdate = ForeignKey.CASCADE
            )
        ],
        indices = [
            Index(
                    value = ["question_id", "form_id", "option_id"],
                    unique = true
            )
        ]
)
data class EntityQuestionOption(

        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "question_option_id") val idQuestionOption: Long = 0,

        @ColumnInfo(name = "question_id") val idQuestion: Long,
        @ColumnInfo(name = "form_id") val idForm: Long,


        @ColumnInfo(name = "option_id") val idOption: String,
        val description: String,
        val selected: Boolean = false

) {
    fun toModel() = Spinnable(
            id = idOption,
            description = description,
            selected = selected
    )

    fun toEntityAnswer(idAnswer: Long) = EntityAnswerOption(
            idAnswer = idAnswer,
            idQuestionOption = idQuestionOption
    )
}