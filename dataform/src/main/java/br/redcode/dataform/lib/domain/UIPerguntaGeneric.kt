package br.redcode.dataform.lib.domain

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import br.redcode.dataform.lib.R
import br.redcode.dataform.lib.interfaces.Questionable
import br.redcode.dataform.lib.model.Question
import br.redcode.dataform.lib.model.QuestionSettings
import br.redcode.dataform.lib.ui.UIIndicator
import br.redcode.dataform.lib.utils.Constants.EMPTY_STRING
import br.redcode.dataform.lib.utils.Constants.PREFFIX_QUESTION

/**
 * Created by pedrofsn on 31/10/2017.
 */
abstract class UIPerguntaGeneric(val idLayout: Int, val question: Question, val configuracao: QuestionSettings) : Questionable {

    private lateinit var textViewLabel: TextView
    private lateinit var textViewInformacao: TextView
    lateinit var UIIndicator: UIIndicator

    open fun initialize(context: Context): View {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(idLayout, null)
        view.tag = "$PREFFIX_QUESTION${question.id}"
        initView(view)
        populateView()
        return view
    }

    open fun initView(view: View) {
        UIIndicator = view.findViewById(R.id.indicador)
        textViewLabel = view.findViewById(R.id.textViewLabel)
        textViewInformacao = view.findViewById(R.id.textViewInformacao)
    }

    open fun populateView() {
        UIIndicator.let {
            it.configuracao = configuracao
            it.setInformacao(getMessageInformation())
        }

        textViewLabel.text = question.getDescriptionWithSymbolRequired()

        textViewInformacao.let {
            textViewInformacao.text = question.information
            textViewInformacao.visibility = if (question.information?.length ?: 0 > 0) View.VISIBLE else View.GONE
        }
    }

    override fun showMessageForErrorFill(isPreenchidoCorretamente: Boolean) {
        UIIndicator.let {
            if (isPreenchidoCorretamente.not()) it.setErro(getMessageErrorFill()) else it.hide()
        }
    }

    override fun getMessageInformation(): String {
        val messageDefaultLimits = question.limit?.getMessageDefault(textViewLabel.context)
        val messageInformation = question.information
        val result = if (messageInformation?.isEmpty() == true) messageDefaultLimits else messageInformation
        return result ?: EMPTY_STRING
    }

    override fun isRequired() = question.required

}