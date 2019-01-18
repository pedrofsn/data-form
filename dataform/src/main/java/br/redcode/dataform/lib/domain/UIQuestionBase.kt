package br.redcode.dataform.lib.domain

import android.content.Context
import android.view.LayoutInflater
import android.view.View
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

    private lateinit var textViewDescription: TextView
    private lateinit var textViewInformation: TextView
    lateinit var uiIndicator: UIIndicator

    open fun initialize(context: Context): View {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(idLayout, null)
        view.tag = "$PREFFIX_QUESTION${question.id}"
        initView(view)
        populateView()
        return view
    }

    open fun initView(view: View) {
        uiIndicator = view.findViewById(R.id.indicator)
        textViewDescription = view.findViewById(R.id.textViewDescription)
        textViewInformation = view.findViewById(R.id.textViewInformation)
    }

    open fun populateView() {
        uiIndicator.let {
            it.settings = settings
            it.setInformation(getMessageInformation())
        }

        textViewDescription.text = question.getDescriptionWithSymbolRequired()

        if (settings.showIndicatorInformation.not()) {
            textViewInformation.text = question.information
        }

        textViewInformation.visibility = if (settings.showIndicatorInformation.not() && question.information?.isNotEmpty() == true) View.VISIBLE else View.GONE
    }

    override suspend fun showMessageForErrorFill(isFilledRight: Boolean) {
        launch(Dispatchers.Main) {
            uiIndicator.let {
                if (isFilledRight.not()) it.setError(getMessageErrorFill()) else it.hide()
            }
        }
    }

    override fun getMessageInformation(): String {
        val messageDefaultLimits = question.limit?.getMessageDefault(textViewDescription.context)
        val messageInformation = question.information
        val result = if (messageInformation?.isEmpty() == true) messageDefaultLimits else messageInformation
        return result ?: EMPTY_STRING
    }

    override fun getAnswer(): Answer {
        return Answer(idQuestion = question.id)
    }

}