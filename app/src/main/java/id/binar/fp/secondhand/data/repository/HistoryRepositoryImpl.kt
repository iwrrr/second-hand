package id.binar.fp.secondhand.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import id.binar.fp.secondhand.data.source.network.ApiService
import id.binar.fp.secondhand.domain.model.History
import id.binar.fp.secondhand.domain.repository.HistoryRepository
import id.binar.fp.secondhand.util.Result
import retrofit2.HttpException
import javax.inject.Inject

class HistoryRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : HistoryRepository {

    override fun getHistory(): LiveData<Result<List<History>>> = liveData {
        emit(Result.Loading)
        try {
            val data = apiService.getHistory().map { it.toDomain() }
            emit(Result.Success(data))
        } catch (e: HttpException) {
            emit(Result.Error(e.message()))
        } catch (e: NullPointerException) {
            emit(Result.Error(e.localizedMessage?.toString() ?: "Data not found"))
        } catch (e: Exception) {
            emit(Result.Error(e.localizedMessage?.toString() ?: "Unknown Error"))
        }
    }

    override fun getHistoryById(id: Int): LiveData<Result<History>> = liveData {
        emit(Result.Loading)
        try {
            val data = apiService.getHistoryById(id).toDomain()
            emit(Result.Success(data))
        } catch (e: HttpException) {
            emit(Result.Error(e.message()))
        } catch (e: NullPointerException) {
            emit(Result.Error(e.localizedMessage?.toString() ?: "Data not found"))
        } catch (e: Exception) {
            emit(Result.Error(e.localizedMessage?.toString() ?: "Unknown Error"))
        }
    }
}