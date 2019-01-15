package br.redcode.dataform.lib.ui

import android.content.Context
import android.graphics.Color
import android.view.View
import android.widget.LinearLayout
import br.redcode.dataform.lib.domain.UIQuestionBase
import br.redcode.dataform.lib.domain.handlers.HandlerCaptureImage
import br.redcode.dataform.lib.domain.handlers.HandlerInputPopup
import br.redcode.dataform.lib.interfaces.Questionable
import br.redcode.dataform.lib.model.FormQuestions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

/**
 * Created by pedrofsn on 31/10/2017.
 */
class UIForm(val formQuestions: FormQuestions, val handlerCaptureImage: HandlerCaptureImage, val handlerInputPopup: HandlerInputPopup) {

    private val perguntasUI = ArrayList<UIQuestionBase>()

    private fun generateUIQuestionsObjects() {
        if (formQuestions.questions.isNotEmpty()) {
            perguntasUI.clear()

            val settings = formQuestions.settings

            for (question in formQuestions.questions) {
                val uiPergunta: UIQuestionBase? = when {
                    question.isQuestionInformativeText() -> UIQuestionInformationText(question, settings)
                    question.isQuestionTextual() -> UIQuestionTextual(question, settings)
                    question.isQuestionObjectiveList() -> UIQuestionObjective(question, settings)
                    question.isQuestionListRemovable() -> UIQuestionListItemRemovable(question, settings, handlerInputPopup)
                    question.isQuestionObjectiveSpinner() -> UIQuestionObjectiveSpinner(question, settings)
                    question.isQuestionMultipleChoice() -> UIQuestionMultipleChoice(question, settings)
                    question.isQuestionImageCameraOrGallery() -> UIQuestionImage(question, settings, handlerCaptureImage, UIQuestionImage.Type.CAMERA_OR_GALLERY)
                    question.isQuestionImageOnlyCamera() -> UIQuestionImage(question, settings, handlerCaptureImage, UIQuestionImage.Type.CAMERA)
                    question.isQuestionImagemOnlyGallery() -> UIQuestionImage(question, settings, handlerCaptureImage, UIQuestionImage.Type.GALLERY)
                    question.isQuestionPercentage() -> UIQuestionPercentage(question, settings)
                    else -> null
                }

                uiPergunta?.let { perguntasUI.add(it) }
            }
        }
    }

    suspend fun getView(context: Context): View = coroutineScope {
        val asyncLinearLayout = async(Dispatchers.IO) {
            val linearLayout = LinearLayout(context)
            linearLayout.orientation = LinearLayout.VERTICAL

            formQuestions.settings.backgroundColor?.let {
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

    suspend fun isQuestionsFilledCorrect(): Boolean = coroutineScope {
        val async = async(Dispatchers.IO) {
            var quantityQuestionsFilledCorrect = 0

            for (ui in perguntasUI) {
                val required = ui.question.required
                val isFilledCorrect = if (required) ui.isFilledCorrect() else true

                ui.showMessageForErrorFill(isFilledCorrect)
                quantityQuestionsFilledCorrect += if (isFilledCorrect) 1 else 0
            }

            quantityQuestionsFilledCorrect == formQuestions.questions.size
        }

        return@coroutineScope async.await()
    }

    fun refreshAnswers() {
        for (ui in perguntasUI) {
            (ui as Questionable).getAnswer().id = ui.question.id
        }
    }

}