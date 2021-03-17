package br.redcode.sample.data.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import br.redcode.dataform.lib.model.FormAnswered
import java.util.*

@Entity(
    tableName = "form_with_answers",
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
            value = ["form_id"],
            unique = false
        )
    ]
)
data class EntityFormAnswered(

    @PrimaryKey(autoGenerate = true)
    val form_with_answers_id: Long = 0,

    val form_id: Long,
    val last_update: Date = Date()
) {
    fun toModel() = FormAnswered(
        id = form_with_answers_id,
        idForm = form_id,
        lastUpdate = last_update
    )
}