package br.redcode.dataform.lib.adapter.viewholder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import br.redcode.dataform.lib.R
import br.redcode.dataform.lib.domain.ViewHolderGeneric
import br.redcode.dataform.lib.interfaces.DuasLinhas
import br.redcode.dataform.lib.interfaces.OnItemClickListener

/**
 * Created by pedrofsn on 31/10/2017.
 */
class ViewHolderItemRemovivel(itemView: View) : ViewHolderGeneric<DuasLinhas>(itemView) {

    private lateinit var textViewLinha1: TextView
    private lateinit var textViewLinha2: TextView
    private lateinit var imageViewRemoverItem: ImageView

    override fun popular(obj: DuasLinhas) {
        textViewLinha1 = itemView.findViewById<TextView>(R.id.textViewLinha1)
        textViewLinha2 = itemView.findViewById<TextView>(R.id.textViewLinha2)
        imageViewRemoverItem = itemView.findViewById<ImageView>(R.id.imageViewRemoverItem)

        textViewLinha1.text = obj.linha1
        textViewLinha2.text = obj.linha2
    }

    override fun popular(obj: DuasLinhas, click: OnItemClickListener?) {
        popular(obj)

        click?.let { imageViewRemoverItem.setOnClickListener { click.onItemClickListener(adapterPosition) } }
    }

}