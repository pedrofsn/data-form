package br.redcode.dataform.lib.adapter.viewholder

import android.view.View
import android.widget.CheckBox
import br.com.redcode.spinnable.library.model.Spinnable
import br.redcode.dataform.lib.R
import br.redcode.dataform.lib.domain.ViewHolderGeneric
import br.redcode.dataform.lib.interfaces.OnItemClickListener
import br.redcode.dataform.lib.model.ConfiguracaoFormulario

/**
 * Created by pedrofsn on 31/10/2017.
 */
class ViewHolderCheckBox(itemView: View) : ViewHolderGeneric<Spinnable>(itemView) {

    private lateinit var checkBox: CheckBox

    fun popular(obj: Spinnable, click: OnItemClickListener?, configuracaoFormulario: ConfiguracaoFormulario) {
        super.popular(obj, click)
        checkBox.isEnabled = configuracaoFormulario.editavel
    }

    override fun popular(obj: Spinnable) {
        checkBox = itemView.findViewById(R.id.checkBox)

        checkBox.text = obj.description
        checkBox.isChecked = obj.selected
    }

}