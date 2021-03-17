package br.redcode.sample.data.dao

import androidx.room.Dao
import androidx.room.Query
import br.redcode.sample.data.entities.EntityQuestionLimit
import org.intellij.lang.annotations.Language

@Dao
interface QuestionLimitDAO : BaseDAO<EntityQuestionLimit> {

    @Language("RoomSql")
    @Query("SELECT * FROM question_limits WHERE limit_id = :idLimit")
    fun readByForm(idLimit: Long): EntityQuestionLimit?
}