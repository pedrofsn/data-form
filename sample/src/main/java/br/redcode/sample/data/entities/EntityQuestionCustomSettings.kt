package br.redcode.sample.data.entities

import androidx.room.*

@Entity(
        tableName = "question_custom_settings",
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
data class EntityQuestionCustomSettings(

        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "custom_settings_id") val idCustomSettings: Long = 0,

        @ColumnInfo(name = "question_id") val idQuestion: Long,

        val key: String,
        val value: Boolean

)