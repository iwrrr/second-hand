package id.binar.fp.secondhand.domain.repository

import androidx.lifecycle.LiveData
import id.binar.fp.secondhand.domain.model.Category
import id.binar.fp.secondhand.util.Result

interface CategoryRepository {

    fun getCategory(): LiveData<Result<List<Category>>>
}