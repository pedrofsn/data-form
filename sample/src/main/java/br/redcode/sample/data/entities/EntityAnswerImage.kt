package br.redcode.sample.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
        tableName = "answer_images",
        foreignKeys = arrayOf(
                ForeignKey(
                        entity = EntityAnswer::class,
                        parentColumns = arrayOf("answer_id"),
                        childColumns = arrayOf("answer_id"),
                        onDelete = ForeignKey.CASCADE,
                        onUpdate = ForeignKey.CASCADE)
        )
)
data class EntityAnswerImage(

        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "image_id") val id: Long = 0,

        @ColumnInfo(name = "answer_id") val idAnswer: Long,

        val image: String,
        val subtitle: String? = null

)

/*
@Entity(
        tableName = "answer_limits",
        foreignKeys = arrayOf(
                ForeignKey(
                        entity = EntityAnswer::class,
                        parentColumns = arrayOf("answer_id"),
                        childColumns = arrayOf("answer_id"),
                        onDelete = ForeignKey.CASCADE,
                        onUpdate = ForeignKey.CASCADE)
        )
)
data class EntityQuestionLimit(

        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "limit_id") val id: Long = 0,

        @ColumnInfo(name = "answer_id") val idAnswer: Long,

        val min: Int,
        val max: Int

)
*/