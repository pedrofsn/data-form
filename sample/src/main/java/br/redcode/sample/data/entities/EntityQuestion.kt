package br.redcode.sample.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import br.redcode.dataform.lib.utils.Constants.EMPTY_STRING

@Entity(tableName = "questions")
data class EntityQuestion(

        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "question_id") val id: Long = 0,

        val description: String,
        val type: String,
        val information: String? = EMPTY_STRING,
        val required: Boolean = true,
        val format: String? = null

)