package br.redcode.dataform.lib.adapter.viewholder

import android.view.View
import android.widget.ImageView
import br.redcode.dataform.lib.R
import br.redcode.dataform.lib.domain.ViewHolderGeneric
import br.redcode.dataform.lib.interfaces.CallbackViewHolderImagem
import br.redcode.dataform.lib.model.Image
import br.redcode.dataform.lib.model.QuestionSettings

/**
 * Created by pedrofsn on 31/10/2017.
 */
open class ViewHolderImage(itemView: View) : ViewHolderGeneric<Image>(itemView) {

    private lateinit var imageViewPreview: ImageView
    private lateinit var imageViewRemover: ImageView

    fun popular(obj: Image, callback: CallbackViewHolderImagem) {
        popular(obj)
        click(imageViewPreview, function = { callback.visualizeImage(obj) })
        click(imageViewRemover, function = { callback.removeImage(adapterPosition) })
        callback.loadImage(obj.image, imageViewPreview)
    }

    override fun popular(obj: Image) {
        imageViewPreview = itemView.findViewById<ImageView>(R.id.imageViewPreview)
        imageViewRemover = itemView.findViewById<ImageView>(R.id.imageViewRemover)
    }

    fun popular(obj: Image, callback: CallbackViewHolderImagem, configuracao: QuestionSettings) {
        popular(obj, callback)
        imageViewRemover.visibility = if (configuracao.editable) View.VISIBLE else View.GONE
    }

}