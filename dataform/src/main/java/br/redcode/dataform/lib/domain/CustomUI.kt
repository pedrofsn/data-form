package br.redcode.dataform.lib.domain

import android.content.Context
import android.view.LayoutInflater

/**
 * Created by pedrofsn on 31/10/2017.
 */
abstract class CustomUI(val contex: Context, idLayout: Int) {

    private val inflater = LayoutInflater.from(contex)
    val view = inflater.inflate(idLayout, null)

    open fun initView() {

    }

    open fun populate() {

    }

}