package br.redcode.dataform.lib.adapter

import android.view.View
import br.com.redcode.spinnable.library.model.Spinnable
import br.redcode.dataform.lib.R
import br.redcode.dataform.lib.adapter.viewholder.ViewHolderItemRemovivel
import br.redcode.dataform.lib.domain.AdapterGeneric
import br.redcode.dataform.lib.interfaces.OnItemClickListener
import br.redcode.dataform.lib.model.ConfiguracaoFormulario

/**
 * Created by pedrofsn on 31/10/2017.
 */
class AdapterItemRemovivel(override var myOnItemClickListener: OnItemClickListener?, val configuracao: ConfiguracaoFormulario) : AdapterGeneric<Spinnable, ViewHolderItemRemovivel>() {

    override val layout: Int = R.layout.adapter_item_removivel

    override fun getViewHolder(view: View): ViewHolderItemRemovivel {
        return ViewHolderItemRemovivel(view)
    }

    override fun onBindViewHolder(holder: ViewHolderItemRemovivel, position: Int) {
        holder.popular(getLista()[position], myOnItemClickListener, configuracao)
    }
}