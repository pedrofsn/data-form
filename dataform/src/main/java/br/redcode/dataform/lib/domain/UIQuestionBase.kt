package br.redcode.dataform.lib.domain

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import br.redcode.dataform.lib.R
import br.redcode.dataform.lib.interfaces.Questionable
import br.redcode.dataform.lib.model.Answer
import br.redcode.dataform.lib.model.FormSettings
import br.redcode.dataform.lib.model.Question
import br.redcode.dataform.lib.ui.UIIndicator
import br.redcode.dataform.lib.utils.Constants.EMPTY_STRING
import br.redcode.dataform.lib.utils.Constants.PREFFIX_QUESTION
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

/**
 * Created by pedrofsn on 31/10/2017.
 */
abstract class UIQuestionBase(val idLayout: Int, val question: Question, val settings: FormSettings) : Questionable, CoroutineScope {

    val job = Job()
    override val coroutineContext: CoroutineContext
        get() = io()

    fun io() = job + Dispatchers.IO
    fun main() = job + Dispatchers.Main

    var handleAnswer: ((Question) -> Unit)? = null

    private lateinit var textViewDescription: TextView
    private lateinit var textViewInformation: TextView
    private lateinit var textViewPreviewAnswer: TextView
    private lateinit var linearLayoutContent: LinearLayout
    private lateinit var viewWrapper: View
    private lateinit var viewContent: View
    lateinit var uiIndicator: UIIndicator

    internal var tempAnswer: Answer = Answer(idQuestion = question.id)

    open fun initialize(context: Context): View {
        val inflater = LayoutInflater.from(context)
        viewWrapper = inflater.inflate(getLayoutWrapper(), null)

        viewContent = inflater.inflate(idLayout, null)
        viewContent.tag = "$PREFFIX_QUESTION${question.id}"

        linearLayoutContent = viewWrapper.findViewById(R.id.linearLayoutContent)
        linearLayoutContent.addView(viewContent)

        initView(viewContent)
        populateView()

        return viewWrapper
    }

    private fun getLayoutWrapper(): Int = settings.idLayoutWrapper ?: R.layout.ui_question_wrapper

    open fun initView(view: View) {
        uiIndicator = viewWrapper.findViewById(R.id.indicator)
        textViewDescription = viewWrapper.findViewById(R.id.textViewDescription)
        textViewPreviewAnswer = viewWrapper.findViewById(R.id.textViewPreviewAnswer)
        textViewInformation = viewWrapper.findViewById(R.id.textViewInformation)
    }

    open fun populateView() {
        uiIndicator.let {
            it.settings = settings
            it.setInformation(getMessageInformation())
        }

        textViewDescription.text = if (settings.showSymbolRequired) question.getDescriptionWithSymbolRequired() else question.description

        if ((settings.showInformation || settings.showIndicatorInformation) && question.hasInformation()) {
            textViewInformation.text = question.information
            textViewInformation.visibility = View.VISIBLE
        } else {
            textViewInformation.visibility = View.GONE
        }

        if (isInputAnswersInOtherScreen()) {
            viewWrapper.setOnClickListener { handleAnswer?.invoke(question) }
            linearLayoutContent.visibility = View.GONE
            textViewPreviewAnswer.visibility = View.VISIBLE
        } else {
            linearLayoutContent.visibility = View.VISIBLE
            textViewPreviewAnswer.visibility = View.GONE
        }
    }

    override fun showMessageForErrorFill(isFilledRight: Boolean) {
        launch(main()) {
            uiIndicator.apply {
                if (isFilledRight) hide() else setError(getMessageErrorFill())
            }
        }
    }

    override fun getMessageInformation(): String {
        val messageDefaultLimits = question.limit?.getMessageDefault(textViewDescription.context)
        val messageInformation = question.information
        val result = if (messageInformation?.isEmpty() == true) messageDefaultLimits else messageInformation
        return result ?: EMPTY_STRING
    }

    override fun fillAnswer(answer: Answer) {
        if (isInputAnswersInOtherScreen()) {
            textViewPreviewAnswer.text = answer.getPreviewAnswer(question)
        }

        tempAnswer = answer
    }

    fun isInputAnswersInOtherScreen() = settings.inputAnswersInOtherScreen

}