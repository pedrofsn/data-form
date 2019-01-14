package br.redcode.dataform.lib.adapter

import android.view.View
import br.redcode.dataform.lib.R
import br.redcode.dataform.lib.adapter.viewholder.ViewHolderImage
import br.redcode.dataform.lib.adapter.viewholder.ViewHolderImageWithSubtitle
import br.redcode.dataform.lib.domain.AdapterGeneric
import br.redcode.dataform.lib.interfaces.CallbackViewHolderImagem
import br.redcode.dataform.lib.model.Image
import br.redcode.dataform.lib.model.QuestionSettings

/**
 * Created by pedrofsn on 31/10/2017.
 */
class AdapterImage(val callback: CallbackViewHolderImagem, val configuracao: QuestionSettings, val comLegenda: Boolean = false) : AdapterGeneric<Image, ViewHolderImage>() {

    override var myOnItemClickListener: ((Int) -> Unit)? = null

    override val layout: Int = if (comLegenda) R.layout.adapter_image_with_subtitle else R.layout.adapter_image

    override fun getViewHolder(view: View): ViewHolderImage {
        return if (comLegenda) ViewHolderImageWithSubtitle(view) else ViewHolderImage(view)
    }

    override fun onBindViewHolder(holder: ViewHolderImage, position: Int) {
        holder.popular(getList()[position], callback, configuracao)
    }

}