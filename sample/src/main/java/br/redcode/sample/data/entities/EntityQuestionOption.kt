package br.redcode.sample.data.entities

import androidx.room.*

@Entity(
        tableName = "question_options",
        foreignKeys = arrayOf(
                ForeignKey(
                        entity = EntityQuestion::class,
                        parentColumns = arrayOf("question_id"),
                        childColumns = arrayOf("question_id"),
                        onDelete = ForeignKey.CASCADE,
                        onUpdate = ForeignKey.CASCADE
                )
        ),
        indices = [
            Index(
                    value = ["option_id", "question_id"],
                    unique = true
            )
        ]
)
data class EntityQuestionOption(

        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "question_option_id") val idQuestionOption: Long = 0,

        @ColumnInfo(name = "question_id") val idQuestion: Long,
        @ColumnInfo(name = "option_id") val idOption: String,

        val description: String,
        val selected: Boolean = false

)