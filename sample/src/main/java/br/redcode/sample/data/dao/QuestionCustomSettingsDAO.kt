package br.redcode.sample.data.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import br.redcode.sample.data.entities.EntityQuestionCustomSettings

@Dao
interface QuestionCustomSettingsDAO : BaseDAO<EntityQuestionCustomSettings> {

    @Transaction
    fun insertAll(objs: List<EntityQuestionCustomSettings>?) = objs?.forEach { insert(it) }

    @Query("SELECT * FROM question_custom_settings WHERE form_id = :idForm and question_id = :idQuestion")
    fun readAllFromSpecificQuestionFromForm(
        idForm: Long,
        idQuestion: Long
    ): List<EntityQuestionCustomSettings>
}