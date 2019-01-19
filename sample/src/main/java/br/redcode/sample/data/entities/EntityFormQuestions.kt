package br.redcode.sample.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "form_questions")
data class EntityFormQuestions(

        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "form_questions_id") val id: Long = 0,
        @ColumnInfo(name = "last_update") val lastUpdate: Date

)