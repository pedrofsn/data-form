package br.redcode.sample.data.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import br.redcode.dataform.lib.model.Answer
import br.redcode.sample.data.database.MyRoomDatabase
import br.redcode.sample.data.entities.EntityAnswer
import br.redcode.sample.extensions.toEntity

@Dao
interface AnswerDAO : BaseDAO<EntityAnswer> {

    @Query("SELECT * FROM answers WHERE form_id = :idForm and question_id = :idQuestion")
    fun read(idQuestion: Long, idForm: Long): EntityAnswer?

    @Query("DELETE FROM answers WHERE form_id = :idForm")
    fun delete(idForm: Long)

    @Transaction
    fun deleteAndSave(idForm: Long, answers: HashMap<Long, Answer>) {
        delete(idForm = idForm)

        answers.forEach {
            val idQuestion = it.key
            val answer = it.value

            deleteAndSave(
                    idForm = idForm,
                    idQuestion = idQuestion,
                    answer = answer
            )
        }
    }

    @Transaction
    fun deleteAndSave(idForm: Long, idQuestion: Long, answer: Answer) {
        val entityAnswer = answer.toEntity(
                idQuestion = idQuestion,
                idForm = idForm
        )

        val idAnswer = insert(entityAnswer)
        val questionOptions = MyRoomDatabase.getInstance().questionOptionDAO().readAllOptionsFromSpecificQuestionFromForm(
                idForm = idForm,
                idQuestion = idQuestion
        )

        when {
            answer.hasOptions() -> {
                val selecteds = questionOptions.filter { it.idOption in answer.options!! }
                val entities = selecteds.map { it.toEntityAnswer(idAnswer = idAnswer) }
                MyRoomDatabase.getInstance().answerOptionDAO().insertAll(entities)
            }

            answer.hasImages() -> {
                val entityImages = answer.images.toEntity(idAnswer = idAnswer)
                MyRoomDatabase.getInstance().answerImageDAO().insertAll(entityImages)
            }
        }
    }

}