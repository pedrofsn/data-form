package br.redcode.sample.data.dao

import androidx.room.Dao
import androidx.room.Query
import br.redcode.sample.data.entities.EntityAnswer

@Dao
interface AnswerDAO : BaseDAO<EntityAnswer> {

    @Query("SELECT * FROM answers WHERE form_id = :idForm and question_id = :idQuestion")
    fun read(idQuestion: Long, idForm: Long): EntityAnswer?

}