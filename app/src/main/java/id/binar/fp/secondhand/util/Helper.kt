package id.binar.fp.secondhand.util

import android.content.Context
import android.widget.Toast
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
}