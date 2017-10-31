package br.redcode.dataform.lib.domain

import android.content.Context
import android.view.LayoutInflater
import android.view.View

/**
 * Created by pedrofsn on 31/10/2017.
 */
abstract class CustomUI<T>(contex: Context, t: T, val idLayout: Int) {

    private val inflater = LayoutInflater.from(contex)
    lateinit var view: View

    open fun initView() {

    }

    open fun populateView() {

    }

    open fun inicializar(): View {
        view = inflater.inflate(idLayout, null)

        initView()
        populateView()
        return view;
    }

}