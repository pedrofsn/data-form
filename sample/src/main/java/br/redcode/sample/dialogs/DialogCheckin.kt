package br.redcode.sample.dialogs

import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import br.redcode.sample.R
import br.redcode.sample.domain.DialogFragmentGeneric
import br.redcode.sample.interfaces.OnPosicaoCadastrada

/**
 * Created by pedrofsn on 13/11/2017.
 */
class DialogCheckin : DialogFragmentGeneric() {

    override var layout: Int = R.layout.dialog_checkin
    override var canceledOnTouchOutside: Boolean = false

    lateinit var editTextLatitude: EditText
    lateinit var editTextLongitude: EditText
    lateinit var buttonAdicionar: Button

    private lateinit var callback: OnPosicaoCadastrada

    companion object {
        val PARAMETRO = "PARAMETRO"

        fun customShow(activity: AppCompatActivity, callback: OnPosicaoCadastrada) {
            DialogCheckin().apply {
                arguments = Bundle().apply {
                    putSerializable(PARAMETRO, callback)
                }
            }.show(activity.supportFragmentManager, "DialogCheckin")
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        callback = arguments?.getSerializable(PARAMETRO) as OnPosicaoCadastrada
        return super.onCreateDialog(savedInstanceState)
    }

    override fun initView(view: View?) {
        view?.let {
            editTextLatitude = it.findViewById<EditText>(R.id.editTextLatitude)
            editTextLongitude = it.findViewById<EditText>(R.id.editTextLongitude)
            buttonAdicionar = it.findViewById<Button>(R.id.buttonAdicionar)
        }
    }

    override fun afterOnCreate() {
        buttonAdicionar.setOnClickListener {
            callback.onPosicaoCadastrada(
                editTextLatitude.text.toString(),
                editTextLongitude.text.toString()
            )
            dismiss()
        }
    }
}

