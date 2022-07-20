package id.binar.fp.secondhand.util

import android.content.Context
import android.graphics.Color
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textview.MaterialTextView
import id.binar.fp.secondhand.R
import id.binar.fp.secondhand.domain.model.Category
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

object Helper {

    fun dateFormatter(date: String?): String? {
        val inputPattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
        val outputPattern = "d MMM, HH:mm"

        val inputFormat = SimpleDateFormat(inputPattern, Locale.getDefault())
        val outputFormat = SimpleDateFormat(outputPattern, Locale.getDefault())

        return if (date != null) {
            val inputDate = inputFormat.parse(date)
            inputDate?.let {
                outputFormat.format(it)
            }
        } else {
            ""
        }
    }

    fun numberFormatter(number: Int?): String {
        val formatter = DecimalFormat("#,###,###")
        return formatter.format(number)
    }

    fun initCategory(categories: List<Category>?): String {
        if (categories.isNullOrEmpty()) {
            return "-"
        }

        val list = ArrayList<Category>()
        categories.forEach { category -> list.add(category) }
        return list.joinToString { it.name }
    }

    fun showToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun showSnackbar(context: Context, view: View, text: String) {
        val snackbar = Snackbar.make(view, "", Snackbar.LENGTH_LONG)
        val customSnackView = LayoutInflater.from(context).inflate(R.layout.layout_snackbar, null)

        val snackbarView = snackbar.view
        val params = snackbarView.layoutParams as CoordinatorLayout.LayoutParams

        params.gravity = Gravity.TOP
        params.setMargins(20, 220, 20, 0)

        snackbarView.layoutParams = params
        snackbarView.setBackgroundColor(Color.TRANSPARENT)

        val snackbarLayout = snackbarView as Snackbar.SnackbarLayout
        snackbarLayout.setPadding(0, 0, 0, 0)

        val message = customSnackView.findViewById<MaterialTextView>(R.id.tv_message)
        val btnClose = customSnackView.findViewById<ShapeableImageView>(R.id.btn_close)

        message.text = text
        btnClose.setOnClickListener {
            snackbar.dismiss()
        }

        snackbarLayout.addView(customSnackView, 0)
        snackbar.show()
    }
}