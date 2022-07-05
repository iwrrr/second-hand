package id.binar.fp.secondhand.util

import id.binar.fp.secondhand.data.source.network.response.CategoryDto

object Helper {
    fun initCategory(categories:List<CategoryDto>?):String{
        val list = ArrayList<CategoryDto>()
        categories?.forEach { category->list.add(category) }
        return list.joinToString { it.name!! }
    }
}