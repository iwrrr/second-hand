package id.binar.fp.secondhand.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import id.binar.fp.secondhand.data.source.network.ApiService
import id.binar.fp.secondhand.domain.model.History
import id.binar.fp.secondhand.domain.repository.HistoryRepository
import id.binar.fp.secondhand.util.Result
import javax.inject.Inject

class HistoryRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : HistoryRepository {

    override fun getHistory(): LiveData<Result<List<History>>> = liveData {
        TODO("Not yet implemented")
    }

    override fun getHistoryById(id: Int): LiveData<Result<History>> = liveData {
        TODO("Not yet implemented")
    }
}