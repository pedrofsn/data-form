package br.redcode.sample.data.dao

import androidx.room.Dao
import androidx.room.Query
import br.redcode.sample.data.entities.EntityFormAnswered
import java.util.*

@Dao
interface FormAnsweredDAO : BaseDAO<EntityFormAnswered> {

    @Query("UPDATE form_with_answers set last_update = :lastUpdate WHERE form_with_answers_id = :form_with_answers_id")
    fun refreshLastUpdate(form_with_answers_id: Long, lastUpdate: Date)

    @Query("SELECT * FROM form_with_answers WHERE form_id = :idForm")
    fun readAllByIdForm(idForm: Long): List<EntityFormAnswered>

    @Query("SELECT * FROM form_with_answers WHERE form_with_answers_id = :form_with_answers_id")
    fun read(form_with_answers_id: Long): EntityFormAnswered?

    @Query("DELETE FROM form_with_answers")
    fun deleteAll()


}