package id.binar.fp.secondhand.util

import android.graphics.Bitmap
import android.net.Uri
import android.util.Patterns
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.imageview.ShapeableImageView
import id.binar.fp.secondhand.R

object Extensions {

    fun ShapeableImageView.loadImage(url: String?) {
        Glide.with(this.context)
            .load(url)
            .apply(
                RequestOptions
                    .placeholderOf(R.drawable.ic_placeholder_image)
                    .error(R.drawable.ic_placeholder_image)
            )
            .into(this)
    }

    fun ShapeableImageView.loadImage(uri: Uri) {
        Glide.with(this.context)
            .load(uri)
            .into(this)
    }

    fun ShapeableImageView.loadImage(bitmap: Bitmap) {
        Glide.with(this.context)
            .load(bitmap)
            .into(this)
    }

    fun ShapeableImageView.clear() {
        Glide.with(this.context)
            .load(R.drawable.ic_image)
            .into(this)
    }

    fun String.isValidated(): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(this).matches()
    }

}