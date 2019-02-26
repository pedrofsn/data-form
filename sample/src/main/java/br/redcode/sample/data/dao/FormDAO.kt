package br.redcode.sample.data.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import br.com.redcode.spinnable.library.model.Spinnable
import br.redcode.dataform.lib.model.Answer
import br.redcode.dataform.lib.model.Form
import br.redcode.dataform.lib.model.Question
import br.redcode.sample.data.database.MyRoomDatabase
import br.redcode.sample.data.entities.EntityForm
import java.util.*

@Dao
interface FormDAO : BaseDAO<EntityForm> {

    @Query("UPDATE forms set last_update = :lastUpdate WHERE form_id = :idForm")
    fun refreshLastUpdate(idForm: Long, lastUpdate: Date)

    @Query("SELECT count(*) > 1 FROM forms WHERE form_id = :idForm")
    fun exists(idForm: Long): Boolean

    @Query("SELECT * FROM forms WHERE form_id = :idForm")
    fun read(idForm: Long): EntityForm?

    @Transaction
    fun readForm(idForm: Long): Form? {
        val db = MyRoomDatabase.getInstance()

        val entityForm = read(idForm)
        val entityFormSettings = db.formSettingsDAO().readByForm(idForm)

        if (entityForm != null && entityFormSettings != null) {
            val entityQuestions = db.questionDAO().readByForm(idForm)

            val questions = arrayListOf<Question>()
            val answers = arrayListOf<Answer>()

            entityQuestions.forEach { entityQuestion ->
                val idQuestion = entityQuestion.idQuestion

                val entityQuestionLimit = db.questionLimitDAO().readByForm(idQuestion)
                val limit = entityQuestionLimit?.toModel()

                val entityQuestionOptions = db.questionOptionDAO().readAllOptionsFromSpecificQuestionFromForm(
                        idQuestion = idQuestion,
                        idForm = idForm
                )
                val options = arrayListOf<Spinnable>()
                options.addAll(entityQuestionOptions.map { it.toModel() })

                val entityQuestionCustomSettings = db.questionCustomSettingsDAO().readAllFromSpecificQuestionFromForm(
                        idQuestion = idQuestion,
                        idForm = idForm
                )
                val customSettings = hashMapOf<String, Boolean>()
                entityQuestionCustomSettings.map { it.toModel() }.forEach { customSettings.put(it.key, it.value) }

                val entityAnswerImages = db.answerImageDAO().readAllInsideQuestionFromForm(
                        idQuestion = idQuestion,
                        idForm = idForm
                )
                val images = entityAnswerImages.map { it.toModel() }

                val entityAnswerOptions = db.answerOptionDAO().readAllInsideQuestionFromForm(
                        idQuestion = idQuestion,
                        idForm = idForm
                )

                val entityAnswer = db.answerDAO().read(
                        idQuestion = idQuestion,
                        idForm = idForm
                )

                val question = entityQuestion.toModel(
                        limit = limit,
                        options = options,
                        customSettings = customSettings
                )

                val answer = entityAnswer?.toModel(
                        images = images,
                        options = entityAnswerOptions
                )
                answer?.let { answers.add(answer) }
                questions.add(question)

            }

            val form = Form(
                    idForm = idForm,
                    lastUpdate = entityForm.lastUpdate,
                    settings = entityFormSettings.toModel(),
                    questions = questions,
                    answers = answers
            )

            return form
        }

        return null
    }

    /*@Transaction
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
    }*/

    @Query("DELETE FROM forms")
    fun deleteAll()

}