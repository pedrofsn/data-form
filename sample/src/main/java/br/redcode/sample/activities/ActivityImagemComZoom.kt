package br.redcode.sample.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.widget.ImageView
import br.redcode.sample.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_imagem_com_zoom.*

/**
 * Created by pedrofsn on 03/11/2017.
 */
class ActivityImagemComZoom : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_imagem_com_zoom)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val imagem: String = intent.getStringExtra("imagem")

        Picasso.with(this)
                .load(imagem)
                .into(photoView as ImageView)
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