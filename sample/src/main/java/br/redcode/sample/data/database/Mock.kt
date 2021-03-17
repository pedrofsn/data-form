package br.redcode.sample.data.database

import br.redcode.sample.data.entities.EntityAnswer
import br.redcode.sample.data.entities.EntityAnswerOption
import br.redcode.sample.data.entities.EntityForm
import br.redcode.sample.data.entities.EntityFormAnswered
import br.redcode.sample.data.entities.EntityFormSettings
import br.redcode.sample.data.entities.EntityQuestion
import br.redcode.sample.data.entities.EntityQuestionOption
import br.redcode.sample.utils.Utils

object Mock {

    const val MOCK_ID_FORM: Long = 10

    fun seedDatabase() {
        Utils.log("Mock -> seedDatabase - start")

        val db = MyRoomDatabase.getInstance()

        db.formDAO().deleteAll()

        // FORM
        val entityForm1 = EntityForm(idForm = MOCK_ID_FORM)

        val idForm = db.formDAO().insert(entityForm1)

        // FORM SETTINGS
        val entityFormSettings = EntityFormSettings(
            idForm = idForm,

            inputAnswersInOtherScreen = true,
            showIndicatorInformation = false,
            showIndicatorError = false,
            showSymbolRequired = false,
            editable = false,
            backgroundColor = null
        )
        db.formSettingsDAO().insert(entityFormSettings)

        // QUESTION 1
        val questionEntity1 = EntityQuestion(
            idForm = idForm,

            description = "What is your favorite candy?",
            type = "objective_list",
            required = true
        )
        val idQuestion1 = db.questionDAO().insert(questionEntity1)

        // QUESTION 1 - OPTION 1
        val option1Question1 = EntityQuestionOption(
            idQuestion = idQuestion1,
            idForm = idForm,

            idOption = "1",
            description = "Honeycomb"
        )

        // QUESTION 1 - OPTION 2
        val option2Question1 = EntityQuestionOption(
            idQuestion = idQuestion1,
            idForm = idForm,

            idOption = "2",
            description = "Jelly Beans"
        )

        // QUESTION 1 - OPTION 3
        val option3Question1 = EntityQuestionOption(
            idQuestion = idQuestion1,
            idForm = idForm,

            idOption = "3",
            description = "Cupcake"
        )

        // QUESTION 1 - OPTION 4
        val option4Question1 = EntityQuestionOption(
            idQuestion = idQuestion1,
            idForm = idForm,

            idOption = "4",
            description = "Pie"
        )

        val idOption1Question1 = db.questionOptionDAO().insert(option1Question1)
        val idOption2Question1 = db.questionOptionDAO().insert(option2Question1)
        val idOption3Question1 = db.questionOptionDAO().insert(option3Question1)
        val idOption4Question1 = db.questionOptionDAO().insert(option4Question1)

        // FORM ANSWERS
        val entityFormAnswered = EntityFormAnswered(form_id = idForm)
        val form_with_answers_id = db.formAnsweredDAO().insert(entityFormAnswered)

        // QUESTION 1 - ANSWER 1
        val entityAnswer = EntityAnswer(
            form_with_answers_id = form_with_answers_id,
            idQuestion = idQuestion1
        )
        val idAnswer1 = db.answerDAO().insert(entityAnswer)

        // QUESTION 1 - ANSWER 1 - OPTION 0
        val entityAnswerOption0Question1 = EntityAnswerOption(
            idAnswer = idAnswer1,
            idQuestionOption = idOption3Question1
        )

        // QUESTION 1 - ANSWER 1 - OPTION 1
        val entityAnswerOption1Question1 = EntityAnswerOption(
            idAnswer = idAnswer1,
            idQuestionOption = idOption4Question1
        )

        db.answerOptionDAO().insert(entityAnswerOption0Question1, entityAnswerOption1Question1)

        Utils.log("Mock -> seedDatabase - end")
    }
}