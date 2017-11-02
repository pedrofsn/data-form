package br.redcode.sample

import android.content.Context
import android.support.annotation.RawRes
import android.util.Log
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

/**
 * Created by pedrofsn on 31/10/2017.
 */
class JSONReader(val context: Context) {

    fun getStringFromJson(@RawRes raw: Int): String {
        val sb = StringBuffer()
        val br = BufferedReader(InputStreamReader(context.getResources().openRawResource(raw)))

        try {
            var temp: String? = ""

            while (temp != null) {
                temp?.let {
                    if (it.isNotEmpty()) {
                        sb.append(it)
                    }
                    temp = br.readLine()
                }
            }

        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            try {
                br.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

        val resultado = sb.toString()
        Log.e(Constantes.TAG, "JSON lido: " + resultado)
        return resultado
    }

}