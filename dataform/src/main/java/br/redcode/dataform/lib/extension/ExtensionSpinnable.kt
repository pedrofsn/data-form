package br.redcode.dataform.lib.extension

import android.os.Handler
import android.os.Looper
import android.widget.Spinner
import br.com.redcode.spinnable.library.adapter.AdapterSpinneable
import br.com.redcode.spinnable.library.extensions_functions.setOnItemSelected
import br.com.redcode.spinnable.library.model.Spinnable
import br.redcode.dataform.lib.R
import br.redcode.dataform.lib.model.payloads.PayloadOption
import java.util.*

/**
 * Created by pedrofsn on 31/10/2017.
 */
fun Spinnable.toDTO(): PayloadOption {
    return PayloadOption(id.toLong(), selected)
}

fun <T : Spinner> T.setSpinnable2(
    data: List<Spinnable>,
    hasDefault: Boolean = false,
    id: String? = "",
    defaultString: String? = null,
    function: ((String, Int) -> Unit)? = null
): AdapterSpinneable {
    val temp = ArrayList<Spinnable>()
    var adapterSpinner = AdapterSpinneable(context, hasDefault, temp)

    if (data.isNotEmpty()) {
        val default = defaultString ?: context.getString(R.string.select)
        if (hasDefault) temp.add(Spinnable(default, default, false))

        temp.addAll(data)
        adapterSpinner = AdapterSpinneable(context, hasDefault, temp)
        adapter = adapterSpinner

        setIdSpinnable2(data, hasDefault, id)
    }

    function?.let { setOnItemSelected(data, hasDefault, function, id) }

    return adapterSpinner
}

fun <T : Spinner> T.setIdSpinnable2(
    data: List<Spinnable>,
    hasDefault: Boolean = false,
    id: String? = "",
    delayInMillis: Long = 1000
) {
    // Pre-select item
    if (id?.isNotEmpty() == true) {
        for (i in data.indices) {
            val spinnable = data[i]

            if (id.equals(spinnable.id, ignoreCase = true)) {

                Thread(kotlinx.coroutines.Runnable {
                    Looper.prepare()

                    Handler().postDelayed({
                        val positionOfItem = if (hasDefault) i.inc() else i
                        if (selectedItemPosition != positionOfItem) {
                            setSelection(positionOfItem, true)
                        }
                    }, delayInMillis)


                    Looper.loop()
                }).start()

                break
            }
        }
    }
}