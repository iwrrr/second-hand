package id.binar.fp.secondhand.util

import android.content.Context
import android.widget.Toast
import id.binar.fp.secondhand.data.source.network.response.CategoryDto

object Helper {

    fun initCategory(categories: List<CategoryDto>?): String {
        if (categories.isNullOrEmpty()) {
            return "-"
        }

        val list = ArrayList<CategoryDto>()
        categories.forEach { category -> list.add(category) }
        return list.joinToString { it.name!! }
    }

    fun showToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}