package br.redcode.dataform.lib.ui

import android.content.Context
import android.graphics.Color
import android.view.View
import android.widget.LinearLayout
import br.redcode.dataform.lib.domain.UIPerguntaGeneric
import br.redcode.dataform.lib.domain.handlers.HandlerCaptureImage
import br.redcode.dataform.lib.domain.handlers.HandlerInputPopup
import br.redcode.dataform.lib.interfaces.Questionable
import br.redcode.dataform.lib.model.FormQuestions
import br.redcode.dataform.lib.utils.Constants.FORM_UI_BACKGROUND_DEFAULT_COLOR
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

/**
 * Created by pedrofsn on 31/10/2017.
 */
class UIForm(val formQuestions: FormQuestions, val handlerCaptureImage: HandlerCaptureImage, val handlerInputPopup: HandlerInputPopup) {

    private val perguntasUI = ArrayList<UIPerguntaGeneric>()

    private fun generateUIQuestionsObjects() {
        if (formQuestions.questions.isNotEmpty()) {
            perguntasUI.clear()

            val settings = formQuestions.settings

            for (question in formQuestions.questions) {
                val uiPergunta: UIPerguntaGeneric? = when {
                    question.isQuestionInformativeText() -> UIQuestionInformationText(question, settings)
                    question.isQuestionTextual() -> UIQuestionTextual(question, settings)
                    question.isQuestionObjectiveList() -> UIQuestionObjective(question, settings)
                    question.isQuestionListRemovable() -> UIQuestionListItemRemovable(question, settings, handlerInputPopup)
                    question.isQuestionObjectiveSpinner() -> UIQuestionObjectiveSpinner(question, settings)
                    question.isQuestionMultipleChoice() -> UIQuestionMultipleChoice(question, settings)
                    question.isQuestionImageCameraOrGallery() -> UIQuestionImage(question, settings, handlerCaptureImage, UIQuestionImage.Tipo.CAMERA_OU_GALERIA)
                    question.isQuestionImageOnlyCamera() -> UIQuestionImage(question, settings, handlerCaptureImage, UIQuestionImage.Tipo.CAMERA)
                    question.isQuestionImagemOnlyGallery() -> UIQuestionImage(question, settings, handlerCaptureImage, UIQuestionImage.Tipo.GALERIA)
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

            // Alterar cor de fundo do formulário
            val hexColor = formQuestions.settings.backgroundColor
                    ?: FORM_UI_BACKGROUND_DEFAULT_COLOR
            val color = Color.parseColor(hexColor)
            linearLayout.setBackgroundColor(color)


            generateUIQuestionsObjects()

            for (ui in perguntasUI) {
                val view = ui.initialize(context)
                linearLayout.addView(view)
            }

            return@async linearLayout
        }

        return@coroutineScope asyncLinearLayout.await()
    }

    fun isQuestionsFilledCorrect(): Boolean {
        var quantityQuestionsFilledCorrect = 0

        for (ui in perguntasUI) {
            val required = ui.isRequired()
            val isFilledCorrect = if (required) ui.isFilledCorrect() else true

            ui.showMessageForErrorFill(isFilledCorrect)
            quantityQuestionsFilledCorrect += if (isFilledCorrect) 1 else 0
        }

        return quantityQuestionsFilledCorrect == formQuestions.questions.size
    }

    fun getAnswers() {
        for (ui in perguntasUI) {
            (ui as Questionable).getAnswer().id = ui.question.id
        }
    }

}