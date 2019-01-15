package br.redcode.sample

import android.app.Application
import android.content.Context
import com.facebook.stetho.Stetho
import java.lang.ref.WeakReference

class App : Application() {

    companion object {
        private lateinit var mContext: WeakReference<Context>
        fun getContext() = mContext.get()
    }

    override fun onCreate() {
        super.onCreate()
        mContext = WeakReference(this)
        initializeStetho()
    }

    private fun initializeStetho() {
        if (BuildConfig.DEBUG) Stetho.initializeWithDefaults(this)
    }

}
