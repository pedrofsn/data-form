package br.redcode.dataform.lib.adapter.viewholder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import br.com.redcode.spinnable.library.model.Spinnable
import br.redcode.dataform.lib.R
import br.redcode.dataform.lib.domain.ViewHolderGeneric
import br.redcode.dataform.lib.extension.setTextOrHide
import br.redcode.dataform.lib.interfaces.OnItemClickListener
import br.redcode.dataform.lib.model.ConfiguracaoFormulario

/**
 * Created by pedrofsn on 31/10/2017.
 */
class ViewHolderItemRemovivel(itemView: View) : ViewHolderGeneric<Spinnable>(itemView) {

    private lateinit var textViewLinha1: TextView
    private lateinit var imageViewRemoverItem: ImageView

    override fun popular(obj: Spinnable) {
        textViewLinha1 = itemView.findViewById(R.id.textViewLinha1)
        imageViewRemoverItem = itemView.findViewById(R.id.imageViewRemoverItem)

        textViewLinha1.setTextOrHide(obj.description)
    }

    override fun popular(obj: Spinnable, click: OnItemClickListener?) {
        super.popular(obj, click)
        click?.let { imageViewRemoverItem.setOnClickListener { click.onItemClickListener(adapterPosition) } }
    }

    fun popular(duasLinhas: Spinnable, myOnItemClickListener: OnItemClickListener?, configuracao: ConfiguracaoFormulario) {
        popular(duasLinhas, myOnItemClickListener)
        imageViewRemoverItem.visibility = if (configuracao.editavel) View.VISIBLE else View.GONE
    }

}