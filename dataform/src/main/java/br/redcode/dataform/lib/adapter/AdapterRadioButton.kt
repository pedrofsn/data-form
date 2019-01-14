package br.redcode.dataform.lib.adapter

import android.view.View
import br.com.redcode.spinnable.library.model.Spinnable
import br.redcode.dataform.lib.R
import br.redcode.dataform.lib.adapter.viewholder.ViewHolderRadioButton
import br.redcode.dataform.lib.domain.AdapterGeneric

import br.redcode.dataform.lib.model.QuestionSettings

/**
 * Created by pedrofsn on 31/10/2017.
 */
class AdapterRadioButton(val configuracao: QuestionSettings, override var myOnItemClickListener: ((Int) -> Unit)?) : AdapterGeneric<Spinnable, ViewHolderRadioButton>() {

    override val layout: Int = R.layout.adapter_radiobutton

    override fun getViewHolder(view: View): ViewHolderRadioButton {
        return ViewHolderRadioButton(view)
    }

    override fun onBindViewHolder(holder: ViewHolderRadioButton, position: Int) {
        holder.popular(getList()[position], myOnItemClickListener, configuracao)
    }

}