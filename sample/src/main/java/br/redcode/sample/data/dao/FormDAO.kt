package br.redcode.sample.data.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import br.redcode.dataform.lib.model.Answer
import br.redcode.sample.data.database.MyRoomDatabase
import br.redcode.sample.data.entities.EntityForm
import br.redcode.sample.data.entities.EntityQuestion
import br.redcode.sample.data.entities.FormQuestionFull
import br.redcode.sample.extensions.*

@Dao
interface FormDAO : BaseDAO<EntityForm> {


    @Query("SELECT * FROM forms WHERE form_id = :idForm")
    fun read(idForm: Long): EntityForm?

    //    @Query("""
//        SELECT * FROM forms f
//        where f.form_id = :idForm
//    """)
    @Transaction
    fun readForm(idForm: Long): List<EntityQuestion> {
        val db = MyRoomDatabase.getInstance()

        val entityForm = read(idForm)
        val entityFormSettings = db.formSettingsDAO().readByForm(idForm)
        val entityQuestions = db.questionDAO().readByForm(idForm)

        entityQuestions.forEach { entityQuestion ->
            val idQuestion = entityQuestion.idQuestion

            val entityQuestionLimit = db.questionLimitDAO().readByForm(idQuestion)

            val entityQuestionOptions = db.questionOptionDAO().readAllOptionsFromSpecificQuestionFromForm(
                    idQuestion = idQuestion,
                    idForm = idForm
            )

            val entityQuestionCustomSettings = db.questionCustomSettingsDAO().readAllFromSpecificQuestionFromForm(
                    idQuestion = idQuestion,
                    idForm = idForm
            )

            val entityAnswer = db.answerDAO().read(
                    idQuestion = idQuestion,
                    idForm = idForm
            )

            if (entityAnswer != null) {

                val entityAnswerImages = db.answerImageDAO().readAllInsideQuestionFromForm(
                        idQuestion = idQuestion,
                        idForm = idForm
                )

                val entityAnswerOptions = db.answerOptionDAO().readAllInsideQuestionFromForm(
                        idQuestion = idQuestion,
                        idForm = idForm
                )

                val answer = Answer(
                        idQuestion = idQuestion,
                        text = entityAnswer.text,
                        percentage = entityAnswer.percentage,
                        images = entityAnswerImages.toModel(),
                        options = entityAnswerOptions
                )

            }

        }


        return entityQuestions
    }

//    INNER JOIN form_settings fs on fs.form_id = f.form_id
//
//    INNER JOIN questions q on q.form_id = f.form_id
//    INNER JOIN question_custom_settings qs on qs.question_id = q.question_id
//    INNER JOIN question_limits ql on ql.question_id = q.question_id
//
//    INNER JOIN answers a on a.question_id = q.question_id
//    INNER JOIN answer_images ai on ai.answer_id = a.answer_id
//    INNER JOIN answer_options ao on ao.answer_id = a.answer_id


    @Transaction
    fun insert(full: FormQuestionFull) {
        full.apply {

            val idForm = MyRoomDatabase.getInstance().formDAO().insert(full.toEntity())

            questions.forEach { questionFull ->
                val idQuestion = questionFull.question.idQuestion
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