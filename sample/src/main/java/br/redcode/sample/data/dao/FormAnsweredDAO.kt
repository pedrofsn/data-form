package br.redcode.sample.data.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import br.com.redcode.base.mvvm.extensions.isValid
import br.redcode.sample.data.database.MyRoomDatabase
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

    @Transaction
    fun insertOrUpdate(form_id: Long, form_with_answers_id: Long): EntityFormAnswered? {
        val db = MyRoomDatabase.getInstance()

        val entityFormAnswered: EntityFormAnswered? = when {
            form_with_answers_id.isValid() -> db.formAnsweredDAO().read(form_with_answers_id)
            form_id.isValid() -> {
                val entity = EntityFormAnswered(form_id = form_id)
                val id = db.formAnsweredDAO().insert(entity)
                val new = entity.copy(form_with_answers_id = id)
                new
            }
            else -> throw RuntimeException("Neither 'Form' nor 'form answered' exists in database")
        }

        return entityFormAnswered
    }

}