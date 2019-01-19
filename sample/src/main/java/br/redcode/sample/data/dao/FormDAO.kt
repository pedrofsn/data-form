package br.redcode.sample.data.dao

import androidx.room.Dao
import androidx.room.Transaction
import br.redcode.sample.data.database.MyRoomDatabase
import br.redcode.sample.data.entities.EntityForm
import br.redcode.sample.data.entities.FormQuestionFull
import br.redcode.sample.extensions.changeQuestionCustomSettings
import br.redcode.sample.extensions.changeQuestionOptions
import br.redcode.sample.extensions.toEntity
import br.redcode.sample.extensions.toEntityAnswerQuestion

@Dao
interface FormDAO : BaseDAO<EntityForm> {

    @Transaction
    fun insert(full: FormQuestionFull) {
        full.apply {

            val idForm = MyRoomDatabase.getInstance().formDAO().insert(full.toEntity())

            questions.forEach { questionFull ->
                val idQuestion = questionFull.question.id
                MyRoomDatabase.getInstance().questionDAO().insert(questionFull.question)

                if (questionFull.limit != null) {
                    val limit = questionFull.limit.copy(idQuestion = idQuestion)
                    MyRoomDatabase.getInstance().questionLimitDAO().insert(limit)
                }

                val options = questionFull.options?.changeQuestionOptions(idQuestion = idQuestion)
                val customSettings = questionFull.customSettings?.changeQuestionCustomSettings(idQuestion = idQuestion)

                MyRoomDatabase.getInstance().questionOptionDAO().insertAll(options)
                MyRoomDatabase.getInstance().questionCustomSettingsDAO().insertAll(customSettings)
            }

            answers.forEach { answerFull ->

                val idQuestion = answerFull.answer.idQuestion

                val idAwnser = MyRoomDatabase.getInstance().answerDAO().insert(answerFull.answer)

                answerFull.options?.forEach { id ->
                    val optionQuestion = MyRoomDatabase.getInstance().questionOptionDAO().read(
                            idOption = id,
                            idQuestion = idQuestion
                    )

                    MyRoomDatabase.getInstance().answerOptionDAO().insert(optionQuestion.toEntityAnswerQuestion(idAnswer = idAwnser))
                }

                val images = answerFull.images?.toEntity(idAnswer = idAwnser)
                MyRoomDatabase.getInstance().answerImageDAO().insertAll(images)
            }

            MyRoomDatabase.getInstance().formSettingsDAO().insert(settings)
        }
    }
}