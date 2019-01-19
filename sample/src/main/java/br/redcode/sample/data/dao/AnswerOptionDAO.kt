package br.redcode.sample.data.dao

import androidx.room.Dao
import androidx.room.Transaction
import br.redcode.sample.data.entities.EntityAnswerOption

@Dao
interface AnswerOptionDAO : BaseDAO<EntityAnswerOption> {

    @Transaction
    fun insertAll(objs: List<EntityAnswerOption>?) = objs?.forEach { insert(it) }

}