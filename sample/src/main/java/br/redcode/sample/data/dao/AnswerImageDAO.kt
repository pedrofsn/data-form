package br.redcode.sample.data.dao

import androidx.room.Dao
import androidx.room.Transaction
import br.redcode.sample.data.entities.EntityAnswerImage

@Dao
interface AnswerImageDAO : BaseDAO<EntityAnswerImage> {

    @Transaction
    fun insertAll(objs: List<EntityAnswerImage>?) = objs?.forEach { insert(it) }

}
