package br.redcode.sample.utils

import androidx.annotation.RawRes
import br.com.redcode.base.extensions.toLogcat
import br.redcode.sample.App
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

/**
 * Created by pedrofsn on 31/10/2017.
 */
class JSONReader {

    fun getStringFromJson(@RawRes raw: Int): String {
        val sb = StringBuffer()
        val br =
            BufferedReader(InputStreamReader(App.getContext()?.resources?.openRawResource(raw)))

        try {
            var temp: String? = ""

            while (temp != null) {
                temp.let {
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
        "JSON lido: $resultado".toLogcat()
        return resultado
    }
}