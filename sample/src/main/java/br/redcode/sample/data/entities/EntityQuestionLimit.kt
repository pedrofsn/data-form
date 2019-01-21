package br.redcode.sample.data.entities

import androidx.room.*

@Entity(
        tableName = "answer_limits",
        foreignKeys = [
            ForeignKey(
                    entity = EntityQuestion::class,
                    parentColumns = arrayOf("question_id"),
                    childColumns = arrayOf("question_id"),
                    onDelete = ForeignKey.CASCADE,
                    onUpdate = ForeignKey.CASCADE
            )
        ],
        indices = [
            Index(
                    value = ["question_id"],
                    unique = true
            )
        ]
)
data class EntityQuestionLimit(

        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "limit_id") val idLimit: Long = 0,

        @ColumnInfo(name = "question_id") val idQuestion: Long,

        val min: Int,
        val max: Int

)