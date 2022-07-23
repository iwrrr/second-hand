package id.binar.fp.secondhand.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import id.binar.fp.secondhand.data.source.local.room.dao.HistoryDao
import id.binar.fp.secondhand.data.source.network.ApiService
import id.binar.fp.secondhand.domain.model.History
import id.binar.fp.secondhand.domain.repository.HistoryRepository
import id.binar.fp.secondhand.util.Result
import retrofit2.HttpException
import javax.inject.Inject

class HistoryRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val historyDao: HistoryDao
) : HistoryRepository {

    override fun getHistory(): LiveData<Result<List<History>>> = liveData {
        emit(Result.Loading())

        val oldHistory = historyDao.getHistory().map { it.toDomain() }
        emit(Result.Loading(oldHistory))

        try {
            val data = apiService.getHistory().map { it.toEntity() }
            historyDao.deleteHistories()
            historyDao.insertHistories(data)
        } catch (e: HttpException) {
            emit(Result.Error(e.message(), oldHistory))
        } catch (e: NullPointerException) {
            emit(Result.Error(e.localizedMessage?.toString() ?: "Data not found", oldHistory))
        } catch (e: Exception) {
            emit(Result.Error(e.localizedMessage?.toString() ?: "Unknown Error", oldHistory))
        }

        val newHistory = historyDao.getHistory().map { it.toDomain() }
        emit(Result.Success(newHistory))
    }

    override fun getHistoryById(id: Int): LiveData<Result<History>> = liveData {
        emit(Result.Loading())
        try {
            val history = historyDao.getHistoryById(id)?.toDomain()
            if (history != null) {
                emit(Result.Success(history))
            }
        } catch (e: HttpException) {
            emit(Result.Error(e.message()))
        } catch (e: NullPointerException) {
            emit(Result.Error("Riwayat transaksi tidak ditemukan"))
        } catch (e: Exception) {
            emit(Result.Error(e.localizedMessage?.toString() ?: "Unknown Error"))
        }
    }
}