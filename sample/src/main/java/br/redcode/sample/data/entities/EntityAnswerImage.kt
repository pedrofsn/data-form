package br.redcode.sample.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
        tableName = "answer_images",
        foreignKeys = [
            ForeignKey(
                    entity = EntityForm::class,
                    parentColumns = arrayOf("form_id"),
                    childColumns = arrayOf("form_id"),
                    onDelete = ForeignKey.CASCADE,
                    onUpdate = ForeignKey.CASCADE
            ),
            ForeignKey(
                    entity = EntityAnswer::class,
                    parentColumns = arrayOf("answer_id"),
                    childColumns = arrayOf("answer_id"),
                    onDelete = ForeignKey.CASCADE,
                    onUpdate = ForeignKey.CASCADE
            )
        ]
)
data class EntityAnswerImage(

        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "image_id") val id: Long = 0,

        @ColumnInfo(name = "form_id") val idForm: Long,
        @ColumnInfo(name = "answer_id") val idAnswer: Long,

        val image: String,
        val subtitle: String? = null

)