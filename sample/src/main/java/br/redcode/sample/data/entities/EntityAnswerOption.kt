package br.redcode.sample.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
        tableName = "answer_options",
        foreignKeys = arrayOf(
                ForeignKey(
                        entity = EntityQuestionOption::class,
                        parentColumns = arrayOf("question_option_id"),
                        childColumns = arrayOf("question_option_id"),
                        onDelete = ForeignKey.CASCADE,
                        onUpdate = ForeignKey.CASCADE)
        )
)
data class EntityAnswerOption(

        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "answer_option_id") val idAnswerOption: Long = 0,

        @ColumnInfo(name = "question_option_id") val idQuestionOption: Long

)

