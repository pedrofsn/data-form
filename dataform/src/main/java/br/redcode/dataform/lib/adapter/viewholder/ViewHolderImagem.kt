package br.redcode.dataform.lib.adapter.viewholder

import android.view.View
import android.widget.ImageView
import br.redcode.dataform.lib.R
import br.redcode.dataform.lib.domain.ViewHolderGeneric
import br.redcode.dataform.lib.interfaces.CallbackViewHolderImagem
import br.redcode.dataform.lib.model.ConfiguracaoFormulario
import br.redcode.dataform.lib.model.Imagem

/**
 * Created by pedrofsn on 31/10/2017.
 */
open class ViewHolderImagem(itemView: View) : ViewHolderGeneric<Imagem>(itemView) {

    private lateinit var imageViewPreview: ImageView
    private lateinit var imageViewRemover: ImageView

    fun popular(obj: Imagem, callback: CallbackViewHolderImagem) {
        popular(obj)
        click(imageViewPreview, function = { callback.visualizarImagem(obj) })
        click(imageViewRemover, function = { callback.removerImagem(adapterPosition) })
        callback.carregarImagem(obj.imagem, imageViewPreview)
    }

    override fun popular(obj: Imagem) {
        imageViewPreview = itemView.findViewById<ImageView>(R.id.imageViewPreview)
        imageViewRemover = itemView.findViewById<ImageView>(R.id.imageViewRemover)
    }

    fun popular(obj: Imagem, callback: CallbackViewHolderImagem, configuracao: ConfiguracaoFormulario) {
        popular(obj, callback)
        imageViewRemover.visibility = if (configuracao.editavel) View.VISIBLE else View.GONE
    }

}