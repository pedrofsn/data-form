package br.redcode.sample.data.entities

import androidx.room.*
import br.redcode.dataform.lib.utils.Constants.EMPTY_STRING

@Entity(
        tableName = "questions",
        foreignKeys = [
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
                    value = ["question_id", "form_id"],
                    unique = true
            )
        ]
)
data class EntityQuestion(

        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "question_id") val idQuestion: Long = 0,

        @ColumnInfo(name = "form_id") val idForm: Long,

        val description: String,
        val type: String,
        val information: String? = EMPTY_STRING,
        val required: Boolean = true,
        val format: String? = null

)