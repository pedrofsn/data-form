package br.redcode.sample.data.entities

import androidx.room.*

@Entity(
        tableName = "answer_images",
        foreignKeys = [
            ForeignKey(
                    entity = EntityAnswer::class,
                    parentColumns = arrayOf("answer_id"),
                    childColumns = arrayOf("answer_id"),
                    onDelete = ForeignKey.CASCADE,
                    onUpdate = ForeignKey.CASCADE
            )
        ],
        indices = [
            Index(
                    value = ["answer_id"],
                    unique = true
            )
        ]
)
data class EntityAnswerImage(

        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "image_id") val id: Long = 0,

        @ColumnInfo(name = "answer_id") val idAnswer: Long,

        val image: String,
        val subtitle: String? = null

)