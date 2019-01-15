package br.redcode.dataform.lib.adapter

import android.view.View
import br.com.redcode.spinnable.library.model.Spinnable
import br.redcode.dataform.lib.R
import br.redcode.dataform.lib.adapter.viewholder.ViewHolderCheckBox
import br.redcode.dataform.lib.domain.AdapterGeneric

import br.redcode.dataform.lib.model.FormSettings

/**
 * Created by pedrofsn on 31/10/2017.
 */
class AdapterCheckBox(val formSettings: FormSettings, override var myOnItemClickListener: ((Int) -> Unit)?) : AdapterGeneric<Spinnable, ViewHolderCheckBox>() {

    override val layout: Int = R.layout.adapter_checkbox

    override fun getViewHolder(view: View): ViewHolderCheckBox {
        return ViewHolderCheckBox(view)
    }

    override fun onBindViewHolder(holder: ViewHolderCheckBox, position: Int) {
        holder.popular(getList()[position], myOnItemClickListener, formSettings)
    }

}