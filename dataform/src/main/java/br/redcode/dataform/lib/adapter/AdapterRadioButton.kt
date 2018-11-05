package br.redcode.dataform.lib.adapter

import android.view.View
import br.com.redcode.spinnable.library.model.Spinnable
import br.redcode.dataform.lib.R
import br.redcode.dataform.lib.adapter.viewholder.ViewHolderRadioButton
import br.redcode.dataform.lib.domain.AdapterGeneric
import br.redcode.dataform.lib.interfaces.OnItemClickListener
import br.redcode.dataform.lib.model.ConfiguracaoFormulario

/**
 * Created by pedrofsn on 31/10/2017.
 */
class AdapterRadioButton(override var myOnItemClickListener: OnItemClickListener?, val configuracao: ConfiguracaoFormulario) : AdapterGeneric<Spinnable, ViewHolderRadioButton>() {

    override val layout: Int = R.layout.adapter_radiobutton

    override fun getViewHolder(view: View): ViewHolderRadioButton {
        return ViewHolderRadioButton(view)
    }

    override fun onBindViewHolder(holder: ViewHolderRadioButton, position: Int) {
        holder.popular(getLista()[position], myOnItemClickListener, configuracao)
    }

}