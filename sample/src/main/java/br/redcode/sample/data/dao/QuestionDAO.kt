package br.redcode.sample.data.dao

import androidx.room.Dao
import androidx.room.Query
import br.redcode.sample.data.entities.EntityQuestion

@Dao
interface QuestionDAO : BaseDAO<EntityQuestion> {

    @Query("SELECT * FROM questions WHERE form_id = :idForm")
    fun readByForm(idForm: Long): List<EntityQuestion>
}