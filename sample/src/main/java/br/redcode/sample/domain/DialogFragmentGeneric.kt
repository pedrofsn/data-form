package br.redcode.sample.domain

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * Created by pedrofsn on 19/10/2017.
 */
abstract class DialogFragmentGeneric : androidx.fragment.app.DialogFragment() {

    abstract var layout: Int
    abstract var canceledOnTouchOutside: Boolean

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(layout, container, false)
        dialog.setCanceledOnTouchOutside(canceledOnTouchOutside)
        initView(view)
        afterOnCreate()
        return view
    }

    abstract fun initView(view: View?)

    abstract fun afterOnCreate()

}