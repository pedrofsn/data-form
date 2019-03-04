package br.redcode.sample.data.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import br.redcode.dataform.lib.model.Answer
import br.redcode.sample.data.database.MyRoomDatabase
import br.redcode.sample.data.entities.EntityAnswer
import br.redcode.sample.extensions.toEntity
import java.util.*

@Dao
interface AnswerDAO : BaseDAO<EntityAnswer> {

    @Query("SELECT * FROM answers WHERE form_with_answers_id = :form_with_answers_id and question_id = :idQuestion")
    fun read(idQuestion: Long, form_with_answers_id: Long): EntityAnswer?

    @Query("DELETE FROM answers WHERE form_with_answers_id = :form_with_answers_id")
    fun delete(form_with_answers_id: Long)

    @Transaction
    fun deleteAndSave(form_with_answers_id: Long, answers: HashMap<Long, Answer>) {
        delete(form_with_answers_id = form_with_answers_id)

        answers.forEach {
            val idQuestion = it.key
            val answer = it.value

            deleteAndSave(
                    form_with_answers_id = form_with_answers_id,
                    idQuestion = idQuestion,
                    answer = answer
            )
        }
    }

    @Transaction
    fun deleteAndSave(form_with_answers_id: Long, idQuestion: Long, answer: Answer) {
        val db = MyRoomDatabase.getInstance()
        val entityFormAnswered = db.formAnsweredDAO().read(form_with_answers_id)

        if (entityFormAnswered != null) {
            val entityAnswer = answer.toEntity(
                    idQuestion = idQuestion,
                    form_with_answers_id = form_with_answers_id
            )

            val idAnswer = insert(entityAnswer)
            val questionOptions = db.questionOptionDAO().readAllOptionsFromSpecificQuestionFromForm(
                    idForm = entityFormAnswered.form_id,
                    idQuestion = idQuestion
            )

            when {
                answer.hasOptions() -> {
                    val selecteds = questionOptions.filter { it.idOption in answer.options!! }
                    val entities = selecteds.map { it.toEntityAnswer(idAnswer = idAnswer) }
                    db.answerOptionDAO().insertAll(entities)
                }

                answer.hasImages() -> {
                    val entityImages = answer.images.toEntity(idAnswer = idAnswer)
                    db.answerImageDAO().insertAll(entityImages)
                }
            }

            db.formAnsweredDAO().refreshLastUpdate(form_with_answers_id, Date())
        }
    }

    @Transaction
    fun readAnswers(idQuestion: Long, form_with_answers_id: Long): List<Answer> {
        val db = MyRoomDatabase.getInstance()
        val answers = arrayListOf<Answer>()

        val entityAnswerImages = db.answerImageDAO().readAllInsideQuestionFromForm(
                idQuestion = idQuestion,
                form_with_answers_id = form_with_answers_id
        )
        val images = entityAnswerImages.map { it.toModel() }

        val entityAnswerOptions = db.answerOptionDAO().readAllInsideQuestionFromForm(
                idQuestion = idQuestion,
                form_with_answers_id = form_with_answers_id
        )

        val entityAnswer = db.answerDAO().read(
                idQuestion = idQuestion,
                form_with_answers_id = form_with_answers_id
        )

        val answer = entityAnswer?.toModel(
                images = images,
                options = entityAnswerOptions
        )

        answer?.let { answers.add(answer) }

        return answers
    }

}