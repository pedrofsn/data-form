package br.redcode.dataform.lib.ui

import android.content.Context
import android.graphics.Color
import android.view.View
import android.widget.LinearLayout
import br.redcode.dataform.lib.domain.UIQuestionBase
import br.redcode.dataform.lib.domain.handlers.HandlerCaptureImage
import br.redcode.dataform.lib.model.Answer
import br.redcode.dataform.lib.model.Form
import br.redcode.dataform.lib.model.Question
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

/**
 * Created by pedrofsn on 31/10/2017.
 */
class UIForm(val form: Form, val handlerCaptureImage: HandlerCaptureImage, val handleAnswer: ((Question) -> Unit)? = null) {

    private val perguntasUI = ArrayList<UIQuestionBase>()

    private fun generateUIQuestionsObjects() {
        if (form.questions.isNotEmpty()) {
            perguntasUI.clear()

            val settings = form.settings

            for (question in form.questions) {
                val uiPergunta: UIQuestionBase? = when {
                    question.isQuestionInformativeText() -> UIQuestionInformationText(question, settings)
                    question.isQuestionTextual() -> UIQuestionTextual(question, settings)
                    question.isQuestionObjectiveList() -> UIQuestionObjective(question, settings)
//                    question.isQuestionListRemovable() -> UIQuestionListItemRemovable(question, settings, handlerInputPopup) TODO IT WILL DISABLE THIS KIND OF QUESTION IN OLD LIBRARY VERSIONS!
                    question.isQuestionObjectiveSpinner() -> UIQuestionObjectiveSpinner(question, settings)
                    question.isQuestionMultipleChoice() -> UIQuestionMultipleChoice(question, settings)
                    question.isQuestionImageCameraOrGallery() -> UIQuestionImage(question, settings, handlerCaptureImage, UIQuestionImage.Type.CAMERA_OR_GALLERY)
                    question.isQuestionImageOnlyCamera() -> UIQuestionImage(question, settings, handlerCaptureImage, UIQuestionImage.Type.CAMERA)
                    question.isQuestionImageOnlyGallery() -> UIQuestionImage(question, settings, handlerCaptureImage, UIQuestionImage.Type.GALLERY)
                    question.isQuestionPercentage() -> UIQuestionPercentage(question, settings)
                    else -> null
                }

                uiPergunta?.let {
                    uiPergunta.handleAnswer = handleAnswer
                    perguntasUI.add(it)
                }
            }
        }
    }

    suspend fun getViewGenerated(context: Context): View = coroutineScope {
        val asyncLinearLayout = async(Dispatchers.IO) {
            val linearLayout = LinearLayout(context)
            linearLayout.orientation = LinearLayout.VERTICAL

            form.settings.backgroundColor?.let {
                val color = Color.parseColor(it)
                linearLayout.setBackgroundColor(color)
            }

            generateUIQuestionsObjects()

            for (ui in perguntasUI) {
                val view = ui.initialize(context)
                linearLayout.addView(view)
            }

            return@async linearLayout
        }

        return@coroutineScope asyncLinearLayout.await()
    }

    suspend fun fillAnswers(answers: List<Answer>) = coroutineScope {
        launch(Dispatchers.IO) {
            for (ui in perguntasUI) {
                val answer = answers.firstOrNull { it.idQuestion == ui.question.id }
                answer?.let {
                    launch(Dispatchers.Main) {
                        ui.fillAnswer(it)

//                        TODO não tá ajudando -> este delay que "faz sumir os UI INDICATORS" causa um delay na obtenção da resposta que cria um "fantasma"
//                        delay(500)
//                        if (ui.isFilledCorrect()) {
//                            ui.showMessageForErrorFill(true)
//                        }
                    }
                }
            }
        }
    }

    suspend fun isQuestionsFilledCorrect(): Boolean = coroutineScope {
        val async = async(Dispatchers.IO) {
            var quantityQuestionsFilledCorrect = 0

            for (ui in perguntasUI) {
                val required = ui.question.required
                val isFilledCorrect = if (required) ui.isFilledCorrect() else true

                ui.showMessageForErrorFill(isFilledCorrect)
                quantityQuestionsFilledCorrect += if (isFilledCorrect) 1 else 0
            }

            quantityQuestionsFilledCorrect == form.questions.size
        }

        return@coroutineScope async.await()
    }

    suspend fun getAnswers(): List<Answer> = coroutineScope {

        val answers = arrayListOf<Answer>()

        if (isQuestionsFilledCorrect()) {
            val asyncAnswers = async(Dispatchers.Main) {
                for (ui in perguntasUI) {
                    val answer = ui.getAnswer()
                    answers.add(answer)
                }

                answers
            }

            return@coroutineScope asyncAnswers.await()
        }

        return@coroutineScope answers
    }

}