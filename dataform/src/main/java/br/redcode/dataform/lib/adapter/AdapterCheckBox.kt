package br.redcode.dataform.lib.adapter

import android.view.View
import br.redcode.dataform.lib.R
import br.redcode.dataform.lib.adapter.viewholder.ViewHolderCheckBox
import br.redcode.dataform.lib.domain.AdapterGeneric
import br.redcode.dataform.lib.interfaces.OnItemClickListener
import br.redcode.dataform.lib.model.Alternativa

/**
 * Created by pedrofsn on 31/10/2017.
 */
class AdapterCheckBox(override var myOnItemClickListener: OnItemClickListener?) : AdapterGeneric<Alternativa, ViewHolderCheckBox>() {

    override val layout: Int = R.layout.adapter_checkbox

    override fun getViewHolder(view: View): ViewHolderCheckBox {
        return ViewHolderCheckBox(view)
    }

}