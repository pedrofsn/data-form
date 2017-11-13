package br.redcode.dataform.lib.adapter

import android.view.View
import br.redcode.dataform.lib.R
import br.redcode.dataform.lib.adapter.viewholder.ViewHolderItemRemovivel
import br.redcode.dataform.lib.domain.AdapterGeneric
import br.redcode.dataform.lib.interfaces.DuasLinhas
import br.redcode.dataform.lib.interfaces.OnItemClickListener

/**
 * Created by pedrofsn on 31/10/2017.
 */
class AdapterItemRemovivel(override var myOnItemClickListener: OnItemClickListener?) : AdapterGeneric<DuasLinhas, ViewHolderItemRemovivel>() {

    override val layout: Int = R.layout.adapter_item_removivel

    override fun getViewHolder(view: View): ViewHolderItemRemovivel {
        return ViewHolderItemRemovivel(view)
    }
}