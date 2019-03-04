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

    @Query("SELECT * FROM forms")
    fun readAll(): List<EntityForm>

    @Transaction
    fun readFormWithAnswers(form_with_answers_id: Long): Form? {
        val db = MyRoomDatabase.getInstance()

        val entityFormAnswered = db.formAnsweredDAO().read(form_with_answers_id)

        if (entityFormAnswered != null) {
            val idForm = entityFormAnswered.form_id

            val entityForm = db.formDAO().read(idForm)
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

                    val question = entityQuestion.toModel(
                            limit = limit,
                            options = options,
                            customSettings = customSettings
                    )

                    val readedAnswers = db.answerDAO().readAnswers(
                            idQuestion = idQuestion,
                            form_with_answers_id = entityFormAnswered.form_with_answers_id
                    )

                    if (readedAnswers.isNotEmpty()) {
                        answers.addAll(readedAnswers)
                    }

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
        }

        return null
    }

    @Transaction
    fun readOnlyForm(idForm: Long): Form? {
        val db = MyRoomDatabase.getInstance()

        val entityForm = db.formDAO().read(idForm)
        val entityFormSettings = db.formSettingsDAO().readByForm(idForm)

        if (entityForm != null && entityFormSettings != null) {
            val entityQuestions = db.questionDAO().readByForm(idForm)

            val questions = arrayListOf<Question>()

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

                val question = entityQuestion.toModel(
                        limit = limit,
                        options = options,
                        customSettings = customSettings
                )

                questions.add(question)
            }

            val form = Form(
                    idForm = idForm,
                    lastUpdate = entityForm.lastUpdate,
                    settings = entityFormSettings.toModel(),
                    questions = questions,
                    answers = emptyList()
            )

            return form
        }

        return null
    }

    @Query("DELETE FROM forms")
    fun deleteAll()

}