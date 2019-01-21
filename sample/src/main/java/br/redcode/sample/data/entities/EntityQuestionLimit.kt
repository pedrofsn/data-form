package br.redcode.sample.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
        tableName = "answer_limits",
        foreignKeys = [
            ForeignKey(
                    entity = EntityForm::class,
                    parentColumns = arrayOf("form_id"),
                    childColumns = arrayOf("form_id"),
                    onDelete = ForeignKey.CASCADE,
                    onUpdate = ForeignKey.CASCADE
            ),
            ForeignKey(
                    entity = EntityQuestion::class,
                    parentColumns = arrayOf("question_id"),
                    childColumns = arrayOf("question_id"),
                    onDelete = ForeignKey.CASCADE,
                    onUpdate = ForeignKey.CASCADE
            )
        ]
)
data class EntityQuestionLimit(

        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "limit_id") val idLimit: Long = 0,

        @ColumnInfo(name = "form_id") val idForm: Long,
        @ColumnInfo(name = "question_id") val idQuestion: Long,

        val min: Int,
        val max: Int

)