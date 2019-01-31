package br.redcode.sample.data.dao

import androidx.room.Dao
import androidx.room.Query
import br.redcode.sample.data.entities.EntityFormSettings

@Dao
interface FormSettingsDAO : BaseDAO<EntityFormSettings> {

    @Query("SELECT * FROM form_settings WHERE form_id = :idForm")
    fun readByForm(idForm: Long): EntityFormSettings?

}