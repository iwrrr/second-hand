package id.binar.fp.secondhand.util

import android.util.Patterns
import java.text.SimpleDateFormat
import java.util.*

object Extensions {

//    fun ShapeableImageView.loadImage(image: String?) {
//        Glide.with(this.context)
//            .load(BuildConfig.IMG_URL + image)
//            .apply(
//                RequestOptions
//                    .placeholderOf(R.drawable.ic_loading)
//                    .error(R.drawable.ic_error_image)
//            )
//            .into(this)
//    }
//
//    fun ShapeableImageView.loadImage(uri: Uri) {
//        Glide.with(this.context)
//            .load(uri)
//            .apply(
//                RequestOptions
//                    .placeholderOf(R.drawable.ic_loading)
//                    .error(R.drawable.ic_error_image)
//            )
//            .into(this)
//    }
//
//    fun ShapeableImageView.loadImage(bitmap: Bitmap?) {
//        Glide.with(this.context)
//            .load(bitmap)
//            .apply(
//                RequestOptions
//                    .placeholderOf(R.drawable.ic_loading)
//                    .error(R.drawable.ic_error_image)
//            )
//            .into(this)
//    }

    fun String.isValidated(): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(this).matches()
    }

    fun String.toDate(): String? {
        val inputPattern = "yyyy-MM-dd"
        val outputPattern = "yyyy"

        val inputFormat = SimpleDateFormat(inputPattern, Locale.getDefault())
        val outputFormat = SimpleDateFormat(outputPattern, Locale.getDefault())

        val inputDate = inputFormat.parse(this)

        return if (this != "") {
            inputDate?.let {
                "(${outputFormat.format(it)})"
            }
        } else {
            "-"
        }
    }
}