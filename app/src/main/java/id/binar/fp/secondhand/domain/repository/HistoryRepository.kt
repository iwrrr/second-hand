package id.binar.fp.secondhand.domain.repository

import androidx.lifecycle.LiveData
import id.binar.fp.secondhand.data.source.network.response.HistoryDto
import id.binar.fp.secondhand.util.Result

interface HistoryRepository {

    fun getHistory(): LiveData<Result<List<HistoryDto>>>

    fun getHistoryById(id: Int): LiveData<Result<HistoryDto>>
}