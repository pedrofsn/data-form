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

/**
 * Created by pedrofsn on 31/10/2017.
 */
class UIForm(val context: Context, val formQuestions: FormQuestions, val handlerCaptureImage: HandlerCaptureImage, val handlerInputPopup: HandlerInputPopup) {

    private val perguntasUI = ArrayList<UIPerguntaGeneric>()

    fun generateUI() {
        if (formQuestions.questions.isNotEmpty()) {
            perguntasUI.clear()

            val settings = formQuestions.settings

            for (question in formQuestions.questions) {
                val uiPergunta: UIPerguntaGeneric? = when {
                    question.isQuestionInformativeText() -> UIQuestionInformationText(context, question, settings)
                    question.isQuestionTextual() -> UIQuestionTextual(context, question, settings)
                    question.isQuestionObjectiveList() -> UIQuestionObjective(context, question, settings)
                    question.isQuestionListRemovable() -> UIQuestionListItemRemovable(context, question, settings, handlerInputPopup)
                    question.isQuestionObjectiveSpinner() -> UIQuestionObjectiveSpinner(context, question, settings)
                    question.isQuestionMultipleChoice() -> UIQuestionMultipleChoice(context, question, settings)
                    question.isQuestionImageCameraOrGallery() -> UIQuestionImage(context, question, settings, handlerCaptureImage, UIQuestionImage.Tipo.CAMERA_OU_GALERIA)
                    question.isQuestionImageOnlyCamera() -> UIQuestionImage(context, question, settings, handlerCaptureImage, UIQuestionImage.Tipo.CAMERA)
                    question.isQuestionImagemOnlyGallery() -> UIQuestionImage(context, question, settings, handlerCaptureImage, UIQuestionImage.Tipo.GALERIA)
                    question.isQuestionPercentage() -> UIQuestionPercentage(context, question, settings)
                    else -> null
                }

                uiPergunta?.let { perguntasUI.add(it) }
            }
        }
    }

    fun getView(): View {
        val linearLayout = LinearLayout(context)
        linearLayout.orientation = LinearLayout.VERTICAL

        // Alterar cor de fundo do formulário
        val hexColor = formQuestions.settings.backgroundColor ?: FORM_UI_BACKGROUND_DEFAULT_COLOR
        val color = Color.parseColor(hexColor)
        linearLayout.setBackgroundColor(color)

        generateUI()
        for (ui in perguntasUI) {
            linearLayout.addView(ui.initialize())
        }

        return linearLayout
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