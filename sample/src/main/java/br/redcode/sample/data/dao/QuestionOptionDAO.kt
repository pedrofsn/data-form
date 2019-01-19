package br.redcode.sample.data.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import br.redcode.sample.data.entities.EntityQuestionOption

@Dao
interface QuestionOptionDAO : BaseDAO<EntityQuestionOption> {

    @Transaction
    fun insertAll(objs: List<EntityQuestionOption>?) = objs?.forEach { insert(it) }

    @Query("SELECT * FROM question_options WHERE option_id = :idOption and question_id = :idQuestion")
    fun read(idOption: String, idQuestion: Long): EntityQuestionOption

}