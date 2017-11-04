package br.redcode.dataform.lib.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import br.redcode.dataform.lib.R
import br.redcode.dataform.lib.interfaces.Spinnable

/**
 * Created by pedrofsn on 27/10/2017.
 */
class AdapterSpinnerSpinneable(mContext: Context, val mObjects: List<Spinnable>, val textViewResourceId: Int = R.layout.adapter_spinner) : ArrayAdapter<Any>(mContext, textViewResourceId, mObjects) {

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getCustomView(position, parent)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getCustomView(position, parent)
    }

    fun getCustomView(position: Int, parent: ViewGroup): View {
        val inflater = LayoutInflater.from(context)
        val row = inflater.inflate(textViewResourceId, parent, false)
        val label = row.findViewById<View>(android.R.id.text1) as TextView
        label.text = getItem(position)
        return row
    }

    override fun getItem(position: Int): String? {
        return (super.getItem(position) as Spinnable).getTexto()
    }

    fun getSpinnable(position: Int): Spinnable {
        return super.getItem(position) as Spinnable
    }

    fun getSpinnables(): List<Spinnable> {
        return mObjects
    }

}