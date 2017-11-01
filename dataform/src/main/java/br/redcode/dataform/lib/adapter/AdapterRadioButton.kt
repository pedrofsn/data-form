package br.redcode.dataform.lib.adapter

import android.view.View
import br.redcode.dataform.lib.R
import br.redcode.dataform.lib.adapter.viewholder.ViewHolderRadioButton
import br.redcode.dataform.lib.domain.AdapterGeneric
import br.redcode.dataform.lib.interfaces.OnItemClickListener
import br.redcode.dataform.lib.model.Alternativa

/**
 * Created by pedrofsn on 31/10/2017.
 */
class AdapterRadioButton(override var myOnItemClickListener: OnItemClickListener?) : AdapterGeneric<Alternativa, ViewHolderRadioButton>() {

    override val layout: Int = R.layout.adapter_radiobutton

    override fun getViewHolder(view: View): ViewHolderRadioButton {
        return ViewHolderRadioButton(view)
    }

}