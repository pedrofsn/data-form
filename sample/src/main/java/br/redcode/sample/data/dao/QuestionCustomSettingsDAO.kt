package br.redcode.sample.data.dao

import androidx.room.Dao
import androidx.room.Transaction
import br.redcode.sample.data.entities.EntityQuestionCustomSettings

@Dao
interface QuestionCustomSettingsDAO : BaseDAO<EntityQuestionCustomSettings> {

    @Transaction
    fun insertAll(objs: List<EntityQuestionCustomSettings>?) = objs?.forEach { insert(it) }

}