package br.redcode.dataform.lib.adapter

import android.view.View
import br.redcode.dataform.lib.R
import br.redcode.dataform.lib.adapter.viewholder.ViewHolderImagem
import br.redcode.dataform.lib.domain.AdapterGeneric
import br.redcode.dataform.lib.interfaces.OnItemClickListener
import br.redcode.dataform.lib.model.Alternativa

/**
 * Created by pedrofsn on 31/10/2017.
 */
class AdapterImagem(val clickPreview: View.OnClickListener, val clickRemover: View.OnClickListener) : AdapterGeneric<Alternativa, ViewHolderImagem>() {

    override var myOnItemClickListener: OnItemClickListener? = null
    override val layout: Int = R.layout.adapter_radiobutton

    override fun getViewHolder(view: View): ViewHolderImagem {
        return ViewHolderImagem(view)
    }

    override fun onBindViewHolder(holder: ViewHolderImagem, position: Int) {
        holder.popular(getLista()[position], clickPreview, clickRemover)
    }

}