package br.redcode.sample

import android.app.Application

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
    }

}