package br.redcode.dataform.lib.adapter.viewholder

import android.view.View
import android.widget.ImageView
import br.redcode.dataform.lib.R
import br.redcode.dataform.lib.domain.ViewHolderGeneric
import br.redcode.dataform.lib.model.Alternativa

/**
 * Created by pedrofsn on 31/10/2017.
 */
class ViewHolderImagem(itemView: View) : ViewHolderGeneric<Alternativa>(itemView) {

    private lateinit var imageViewPreview: ImageView
    private lateinit var imageViewRemover: ImageView

    fun popular(obj: Alternativa, clickPreview: View.OnClickListener, clickRemover: View.OnClickListener) {
        popular(obj)
        imageViewPreview.setOnClickListener(clickPreview)
        imageViewRemover.setOnClickListener(clickRemover)
    }

    override fun popular(obj: Alternativa) {
        imageViewPreview = itemView.findViewById<ImageView>(R.id.imageViewPreview)
        imageViewRemover = itemView.findViewById<ImageView>(R.id.imageViewRemover)
    }

}