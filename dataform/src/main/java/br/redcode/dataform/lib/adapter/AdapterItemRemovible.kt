package br.redcode.dataform.lib.adapter

import android.view.View
import br.com.redcode.spinnable.library.model.Spinnable
import br.redcode.dataform.lib.R
import br.redcode.dataform.lib.adapter.viewholder.ViewHolderItemRemovible
import br.redcode.dataform.lib.domain.AdapterGeneric

import br.redcode.dataform.lib.model.FormSettings

/**
 * Created by pedrofsn on 31/10/2017.
 */
class AdapterItemRemovible(
    val settings: FormSettings,
    override var myOnItemClickListener: ((Int) -> Unit)?
) : AdapterGeneric<Spinnable, ViewHolderItemRemovible>() {

    override val layout: Int = R.layout.adapter_item_removable

    override fun getViewHolder(view: View): ViewHolderItemRemovible {
        return ViewHolderItemRemovible(view)
    }

    override fun onBindViewHolder(holder: ViewHolderItemRemovible, position: Int) {
        holder.popular(getList()[position], myOnItemClickListener, settings)
    }
}