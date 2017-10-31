package br.redcode.dataform.lib.ui

import android.content.Context
import android.util.Log
import android.widget.Button
import br.redcode.dataform.lib.R
import br.redcode.dataform.lib.domain.CustomUI

/**
 * Created by pedrofsn on 31/10/2017.
 */
class UIPergunta(val contextActivity: Context) : CustomUI(contextActivity, R.layout.ui_pergunta) {

    private lateinit var button: Button

    override fun initView() {
        button = view.findViewWithTag<Button>(R.id.button)
    }

    override fun populate() {
        button.setOnClickListener { Log.e("teste", "teste") }
    }

}