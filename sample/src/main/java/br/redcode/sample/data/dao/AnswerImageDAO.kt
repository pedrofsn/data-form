package br.redcode.sample.data.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import br.redcode.sample.data.entities.EntityAnswerImage

@Dao
interface AnswerImageDAO : BaseDAO<EntityAnswerImage> {

    @Query("SELECT ai.* FROM answer_images ai INNER JOIN answers a on ai.answer_id = a.answer_id INNER JOIN form_with_answers fa on fa.form_with_answers_id = :form_with_answers_id WHERE a.question_id = :idQuestion")
    fun readAllInsideQuestionFromForm(
        idQuestion: Long,
        form_with_answers_id: Long
    ): List<EntityAnswerImage>

    @Transaction
    fun insertAll(objs: List<EntityAnswerImage>?) = objs?.forEach { insert(it) }

    @Query("DELETE FROM answer_images WHERE answer_id = :idAnswer")
    fun delete(idAnswer: Long)
}
