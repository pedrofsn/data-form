package br.redcode.sample.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "form_settings")
data class EntityFormSettings(

        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "form_settings_id") val idFormSettings: Long = 0,

        val showIndicatorError: Boolean = true,
        val showIndicatorInformation: Boolean = true,
        val showSymbolRequired: Boolean = true,
        val editable: Boolean = true,
        val backgroundColor: String? = null

)