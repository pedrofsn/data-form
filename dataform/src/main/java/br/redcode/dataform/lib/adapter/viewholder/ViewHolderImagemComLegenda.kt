package br.redcode.dataform.lib.adapter.viewholder

import android.view.View
import android.widget.TextView
import br.redcode.dataform.lib.interfaces.OnItemClickListener
import br.redcode.dataform.lib.model.Imagem

/**
 * Created by pedrofsn on 31/10/2017.
 */
class ViewHolderImagemComLegenda(itemView: View) : ViewHolderImagem(itemView) {

    private lateinit var textViewLegenda: TextView

    override fun popular(obj: Imagem, click: OnItemClickListener?) {
        super.popular(obj, click)
        obj.legenda?.let { textViewLegenda.setText(it) }
    }

}