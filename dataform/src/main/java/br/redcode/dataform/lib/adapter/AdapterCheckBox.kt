package br.redcode.dataform.lib.adapter

import android.view.View
import br.redcode.dataform.lib.R
import br.redcode.dataform.lib.adapter.viewholder.ViewHolderCheckBox
import br.redcode.dataform.lib.domain.AdapterGeneric
import br.redcode.dataform.lib.interfaces.OnItemClickListener
import br.redcode.dataform.lib.model.Alternativa
import br.redcode.dataform.lib.model.ConfiguracaoFormulario

/**
 * Created by pedrofsn on 31/10/2017.
 */
class AdapterCheckBox(override var myOnItemClickListener: OnItemClickListener?, val configuracaoFormulario: ConfiguracaoFormulario) : AdapterGeneric<Alternativa, ViewHolderCheckBox>() {

    override val layout: Int = R.layout.adapter_checkbox

    override fun getViewHolder(view: View): ViewHolderCheckBox {
        return ViewHolderCheckBox(view)
    }

    override fun onBindViewHolder(holder: ViewHolderCheckBox, position: Int) {
        holder.popular(getLista()[position], myOnItemClickListener, configuracaoFormulario)
    }

}