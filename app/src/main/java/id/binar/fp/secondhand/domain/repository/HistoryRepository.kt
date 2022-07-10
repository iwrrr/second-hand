package id.binar.fp.secondhand.domain.repository

import androidx.lifecycle.LiveData
import id.binar.fp.secondhand.domain.model.History
import id.binar.fp.secondhand.util.Result

interface HistoryRepository {

    fun getHistory(): LiveData<Result<List<History>>>

    fun getHistoryById(id: Int): LiveData<Result<History>>
}