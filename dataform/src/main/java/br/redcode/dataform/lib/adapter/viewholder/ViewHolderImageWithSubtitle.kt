package br.redcode.dataform.lib.adapter.viewholder

import android.view.View
import android.widget.TextView
import br.redcode.dataform.lib.R
import br.redcode.dataform.lib.model.Image

/**
 * Created by pedrofsn on 31/10/2017.
 */
class ViewHolderImageWithSubtitle(itemView: View) : ViewHolderImage(itemView) {

    private val textViewLegenda: TextView by lazy { itemView.findViewById<TextView>(R.id.textViewLegenda) }

    override fun popular(obj: Image) {
        super.popular(obj)
        obj.subtitle?.let { textViewLegenda.setText(it) }
    }

}