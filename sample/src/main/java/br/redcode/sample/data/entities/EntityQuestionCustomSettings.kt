package br.redcode.sample.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import br.redcode.dataform.lib.model.CustomSetting

@Entity(
    tableName = "question_custom_settings",
    foreignKeys = [
        ForeignKey(
            entity = EntityQuestion::class,
            parentColumns = arrayOf("question_id"),
            childColumns = arrayOf("question_id"),
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = EntityForm::class,
            parentColumns = arrayOf("form_id"),
            childColumns = arrayOf("form_id"),
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(
            value = ["form_id", "question_id"],
            unique = true
        )
    ]
)
data class EntityQuestionCustomSettings(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "custom_settings_id") val idCustomSettings: Long = 0,

    @ColumnInfo(name = "form_id") val idForm: Long,
    @ColumnInfo(name = "question_id") val idQuestion: Long,

    val key: String,
    val value: Boolean

) {
    fun toModel() = CustomSetting(
        key = key,
        value = value
    )
}