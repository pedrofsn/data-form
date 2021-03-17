package br.redcode.sample.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import br.redcode.sample.model.MiniForm
import java.util.*

@Entity(tableName = "forms")
data class EntityForm(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "form_id") val idForm: Long = 0,
    @ColumnInfo(name = "last_update") val lastUpdate: Date = Date()

) {
    fun toModel() = MiniForm(
        id = idForm,
        lastUpdate = lastUpdate
    )
}