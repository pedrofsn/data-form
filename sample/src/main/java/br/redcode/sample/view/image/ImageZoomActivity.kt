package br.redcode.sample.view.image

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment.DIRECTORY_PICTURES
import android.view.Menu
import android.view.MenuItem
import androidx.core.content.FileProvider
import br.com.redcode.base.utils.Constants.EMPTY_STRING
import br.com.redcode.easyglide.library.load
import br.redcode.sample.R
import br.redcode.sample.domain.ActivityWithoutMVVM
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_image_zoom.*
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


class ImageZoomActivity(override val layout: Int = R.layout.activity_image_zoom) : ActivityWithoutMVVM() {

    private val image by lazy { intent?.getStringExtra("image") ?: EMPTY_STRING }
    private val title by lazy { intent?.getStringExtra("title") ?: EMPTY_STRING }
    private val description by lazy { intent?.getStringExtra("description") ?: EMPTY_STRING }

    private val appName by lazy { getString(R.string.app_name) ?: EMPTY_STRING }
    private val shareImage by lazy { getString(R.string.share_image) ?: EMPTY_STRING }

    override fun afterOnCreate() {
        enableHomeAsUpActionBar()
        if (title.isNotBlank()) supportActionBar?.title = title
        if (description.isNotBlank()) supportActionBar?.subtitle = description

        if (image.isNotBlank()) {
            progress.hide()
            photoView.load(image)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_image_with_zoom, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.share) {
            share()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun share() {
        launch(main()) {
            progress.show()
            val bitmapAsync = async(io()) {
                val futureTarget = Glide.with(this@ImageZoomActivity)
                        .asBitmap()
                        .load(image)
                        .submit(1024, 720)

                futureTarget.get()
            }

            val bitmap = bitmapAsync.await()
            progress.hide()

            val uri = getLocalBitmapUri(bitmap, this@ImageZoomActivity)

            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "image/*"
                putExtra(Intent.EXTRA_STREAM, uri)
                putExtra(Intent.EXTRA_TEXT, getNameForShare())
            }

            startActivity(Intent.createChooser(intent, shareImage))
        }
    }

    private fun getNameForShare() = when {
        title.isNotBlank() && description.isNotBlank() -> "$title - $description"
        title.isNotBlank() && description.isBlank() -> title
        title.isBlank() && description.isNotBlank() -> description
        else -> appName
    }

    private fun getLocalBitmapUri(bmp: Bitmap, context: Context): Uri? {
        var bmpUri: Uri? = null
        try {
            val folder = context.getExternalFilesDir(DIRECTORY_PICTURES)
            val file = File(
                    folder,
                    "my_shared_image_" + System.currentTimeMillis() + ".png"
            )
            val out = FileOutputStream(file)
            bmp.compress(Bitmap.CompressFormat.PNG, 100, out)
            out.close()
            bmpUri = FileProvider.getUriForFile(this, "${packageName}.easyphotopicker.fileprovider", file)

            file.deleteOnExit()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return bmpUri
    }

}