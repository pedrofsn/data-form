package br.redcode.sample.data.entities

import androidx.room.*

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
                    entity = EntityForm::class,
                    parentColumns = arrayOf("form_id"),
                    childColumns = arrayOf("form_id"),
                    onDelete = ForeignKey.CASCADE,
                    onUpdate = ForeignKey.CASCADE
            )
        ],
        indices = [
            Index(
                    value = ["form_id", "question_id"],
                    unique = true
            )
        ]
)
data class EntityAnswer(

        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "answer_id") val idAnswer: Long = 0,

        @ColumnInfo(name = "form_id") val idForm: Long,
        @ColumnInfo(name = "question_id") val idQuestion: Long,

        val text: String? = null,
        val percentage: Int? = null

)