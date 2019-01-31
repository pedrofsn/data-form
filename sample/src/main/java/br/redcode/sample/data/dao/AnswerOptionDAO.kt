package br.redcode.sample.data.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import br.redcode.sample.data.entities.EntityAnswerOption

@Dao
interface AnswerOptionDAO : BaseDAO<EntityAnswerOption> {

    // TODO better than add property form_id ? or maybe it's better create a table with this relationship
    @Query("SELECT qo.option_id FROM question_options qo WHERE question_option_id = (SELECT ao.question_option_id FROM answer_options ao INNER JOIN answers a on ao.answer_id = a.answer_id WHERE a.form_id = :idForm and a.question_id = :idQuestion)")
    fun readAllInsideQuestionFromForm(idQuestion: Long, idForm: Long): List<String>

    @Transaction
    fun insertAll(objs: List<EntityAnswerOption>?) = objs?.forEach { insert(it) }

}