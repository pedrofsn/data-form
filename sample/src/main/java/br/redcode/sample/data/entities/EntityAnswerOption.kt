package br.redcode.sample.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
        tableName = "answer_options",
        foreignKeys = arrayOf(
                ForeignKey(
                        entity = EntityAnswer::class,
                        parentColumns = arrayOf("answer_id"),
                        childColumns = arrayOf("answer_id"),
                        onDelete = ForeignKey.CASCADE,
                        onUpdate = ForeignKey.CASCADE)
        )
)
data class EntityAnswerOption(

        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "option_id") val idOption: Long = 0,

        @ColumnInfo(name = "answer_id") val idAnswer: Long,

        val id: String,
        val description: String,
        val selected: Boolean

)