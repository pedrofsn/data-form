package br.redcode.dataform.lib.adapter

import android.view.View
import android.widget.RadioButton
import br.redcode.dataform.lib.R
import br.redcode.dataform.lib.domain.ViewHolderGeneric
import br.redcode.dataform.lib.interfaces.OnItemClickListener
import br.redcode.dataform.lib.model.Alternativa

/**
 * Created by pedrofsn on 31/10/2017.
 */
class ViewHolderRadioButton(itemView: View) : ViewHolderGeneric<Alternativa>(itemView) {

    private lateinit var radioButton: RadioButton

    override fun popular(obj: Alternativa) {
        radioButton = itemView.findViewById<RadioButton>(R.id.radioButton)

        radioButton.text = obj.descricao
        radioButton.isChecked = obj.selecionada
    }

    override fun popular(obj: Alternativa, click: OnItemClickListener?) {
        super.popular(obj, click)
    }

}