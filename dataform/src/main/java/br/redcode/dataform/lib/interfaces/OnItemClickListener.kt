package br.redcode.dataform.lib.interfaces

import java.io.Serializable

/**
 * Created by pedrofsn on 16/10/2017.
 */
interface OnItemClickListener : Serializable {

    fun onItemClickListener(position: Int)

}