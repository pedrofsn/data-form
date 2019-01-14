package br.redcode.dataform.lib.adapter.viewholder

import android.view.View
import android.widget.RadioButton
import br.com.redcode.spinnable.library.model.Spinnable
import br.redcode.dataform.lib.R
import br.redcode.dataform.lib.domain.ViewHolderGeneric

import br.redcode.dataform.lib.model.QuestionSettings

/**
 * Created by pedrofsn on 31/10/2017.
 */
class ViewHolderRadioButton(itemView: View) : ViewHolderGeneric<Spinnable>(itemView) {

    private lateinit var radioButton: RadioButton

    override fun popular(obj: Spinnable) {
        radioButton = itemView.findViewById(R.id.radioButton)

        radioButton.text = obj.description
        radioButton.isChecked = obj.selected
    }

    fun popular(obj: Spinnable, click: ((Int) -> Unit)?, questionSettings: QuestionSettings) {
        super.popular(obj, click)
        radioButton.isEnabled = questionSettings.editable
    }

}