package br.redcode.sample

import android.app.Application
import com.jakewharton.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso

/**
 * Created by pedrofsn on 02/11/2017.
 */
class App : Application() {

    companion object {
        lateinit var instance: App
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        inicializarPicasso()
    }

    private fun inicializarPicasso() {
        val builder = Picasso.Builder(this)
        val cache100mb = 100 * 1024 * 1024
        val downloader = OkHttp3Downloader(this, cache100mb.toLong())

        builder.downloader(downloader)
        builder.listener { _, _, exception -> exception.printStackTrace() }

        val built = builder.build()

        Picasso.setSingletonInstance(built)
    }

}