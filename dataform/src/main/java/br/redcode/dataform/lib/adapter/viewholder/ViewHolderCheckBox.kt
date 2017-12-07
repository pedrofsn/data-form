package br.redcode.dataform.lib.adapter.viewholder

import android.view.View
import android.widget.CheckBox
import br.redcode.dataform.lib.R
import br.redcode.dataform.lib.domain.ViewHolderGeneric
import br.redcode.dataform.lib.interfaces.OnItemClickListener
import br.redcode.dataform.lib.model.Alternativa
import br.redcode.dataform.lib.model.ConfiguracaoFormulario

/**
 * Created by pedrofsn on 31/10/2017.
 */
class ViewHolderCheckBox(itemView: View) : ViewHolderGeneric<Alternativa>(itemView) {

    private lateinit var checkBox: CheckBox

    fun popular(obj: Alternativa, click: OnItemClickListener?, configuracaoFormulario: ConfiguracaoFormulario) {
        super.popular(obj, click)
        checkBox.isEnabled = configuracaoFormulario.editavel
    }

    override fun popular(obj: Alternativa) {
        checkBox = itemView.findViewById<CheckBox>(R.id.checkBox)

        checkBox.text = obj.descricao
        checkBox.isChecked = obj.selecionado
    }

}