package br.redcode.sample.domain

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity

/**
 * Created by pedrofsn on 03/11/2017.
 */
abstract class ActivityGeneric : AppCompatActivity() {

    abstract var ativarBotaoVoltar: Boolean

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (ativarBotaoVoltar) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        item?.let {
            when (it.itemId) {
                android.R.id.home -> onBackPressed()
            }
        }

        return super.onOptionsItemSelected(item)
    }

}