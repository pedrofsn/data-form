package br.redcode.dataform.lib.adapter.viewholder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import br.redcode.dataform.lib.R
import br.redcode.dataform.lib.domain.ViewHolderGeneric
import br.redcode.dataform.lib.extension.setTextOrHide
import br.redcode.dataform.lib.interfaces.DuasLinhas
import br.redcode.dataform.lib.interfaces.OnItemClickListener
import br.redcode.dataform.lib.model.ConfiguracaoFormulario

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

        textViewLinha1.setTextOrHide(obj.getId())
        textViewLinha2.setTextOrHide(obj.getTexto())
    }

    override fun popular(obj: DuasLinhas, click: OnItemClickListener?) {
        super.popular(obj, click)
        click?.let { imageViewRemoverItem.setOnClickListener { click.onItemClickListener(adapterPosition) } }
    }

    fun popular(duasLinhas: DuasLinhas, myOnItemClickListener: OnItemClickListener?, configuracao: ConfiguracaoFormulario) {
        popular(duasLinhas, myOnItemClickListener)
        imageViewRemoverItem.visibility = if (configuracao.editavel) View.VISIBLE else View.GONE
    }

}