package br.redcode.dataform.lib.adapter.viewholder

import android.view.View
import android.widget.TextView
import br.redcode.dataform.lib.R
import br.redcode.dataform.lib.model.Imagem

/**
 * Created by pedrofsn on 31/10/2017.
 */
class ViewHolderImagemComLegenda(itemView: View) : ViewHolderImagem(itemView) {

    private val textViewLegenda: TextView by lazy { itemView.findViewById<TextView>(R.id.textViewLegenda) }

    override fun popular(obj: Imagem) {
        super.popular(obj)
        obj.legenda?.let { textViewLegenda.setText(it) }
    }

}