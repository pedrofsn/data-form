package br.redcode.sample.data.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import br.redcode.sample.data.entities.EntityAnswerImage

@Dao
interface AnswerImageDAO : BaseDAO<EntityAnswerImage> {

    // TODO better than add property form_id ? or maybe it's better create a table with this relationship
    @Query("SELECT ai.* FROM answer_images ai INNER JOIN answers a on ai.answer_id = a.answer_id WHERE a.form_id = :idForm and a.question_id = :idQuestion")
    fun readAllInsideQuestionFromForm(idQuestion: Long, idForm: Long): List<EntityAnswerImage>

    @Transaction
    fun insertAll(objs: List<EntityAnswerImage>?) = objs?.forEach { insert(it) }

    @Query("DELETE FROM answer_images WHERE answer_id = :idAnswer")
    fun delete(idAnswer: Long)

}
