package br.redcode.dataform.lib.extension

import android.util.Log
import android.widget.ImageView
import br.redcode.dataform.lib.R
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

/**
 * Created by pedrofsn on 16/10/2017.
 */

val callback = object : Callback {
    override fun onSuccess() {
        Log.e("teste", "onSuccess")
    }

    override fun onError() {
        Log.e("teste", "onError")
    }
}

val placeholder = android.R.drawable.stat_sys_download
val error = R.drawable.ic_remover

// URL
fun ImageView.load(url: String?) {
    url?.let {
        if (it.isNotEmpty()) {
            var temp = url

            if (it.startsWith("/")) {
                temp = "file://" + it
            }

            Picasso.with(context)
                    .load(temp)
                    .placeholder(placeholder)
                    .error(error)
                    .into(this, callback)
        }
    }
}

fun ImageView.load(url: String?, width: Int, height: Int) {
    url?.let {
        if (it.isNotEmpty()) {
            var temp = url

            if (it.startsWith("/")) {
                temp = "file://" + it
            }

            Log.e("teste", "Carregando: " + temp)

            Picasso.with(context)
                    .load(temp)
                    .placeholder(placeholder)
                    .error(error)
                    .resize(width, height)
                    .into(this, callback)
        }
    }
}

fun ImageView.loadFit(url: String) {
    url.let {
        Picasso.with(context)
                .load(it)
                .placeholder(placeholder)
                .fit()
                .error(error)
                .into(this)
    }
}

// DRAWABLE
fun ImageView.load(drawable: Int) {
    drawable.let {
        Picasso.with(context)
                .load(it)
                .placeholder(placeholder)
                .error(error)
                .into(this)
    }
}

fun ImageView.loadFit(drawable: Int) {
    drawable.let {
        Picasso.with(context)
                .load(it)
                .placeholder(placeholder)
                .fit()
                .error(error)
                .into(this)
    }
}