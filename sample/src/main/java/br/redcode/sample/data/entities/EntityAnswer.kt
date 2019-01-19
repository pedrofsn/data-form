package br.redcode.sample.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
        tableName = "answers",
        foreignKeys = arrayOf(
                ForeignKey(
                        entity = EntityQuestion::class,
                        parentColumns = arrayOf("question_id"),
                        childColumns = arrayOf("question_id"),
                        onDelete = ForeignKey.CASCADE,
                        onUpdate = ForeignKey.CASCADE)
        )
)
data class EntityAnswer(

        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "answer_id") val idAnswer: Long = 0,

        @ColumnInfo(name = "question_id") val idQuestion: Long,

        val text: String? = null,
        val percentage: Int? = null

)