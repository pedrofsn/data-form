package br.redcode.sample.data.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import br.redcode.sample.data.entities.EntityAnswerOption

@Dao
interface AnswerOptionDAO : BaseDAO<EntityAnswerOption> {

    @Query("SELECT qo.option_id FROM question_options qo WHERE question_option_id = (SELECT ao.question_option_id FROM answer_options ao INNER JOIN answers a on ao.answer_id = a.answer_id INNER JOIN form_with_answers fa on fa.form_with_answers_id = :form_with_answers_id WHERE a.question_id = :idQuestion)")
    fun readAllInsideQuestionFromForm(idQuestion: Long, form_with_answers_id: Long): List<String>

    @Transaction
    fun insertAll(objs: List<EntityAnswerOption>?) = objs?.forEach { insert(it) }

    @Query("DELETE FROM answer_options WHERE answer_id = :idAnswer")
    fun delete(idAnswer: Long)

}