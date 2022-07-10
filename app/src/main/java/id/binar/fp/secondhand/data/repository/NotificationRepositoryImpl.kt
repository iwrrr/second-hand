package id.binar.fp.secondhand.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import id.binar.fp.secondhand.data.source.network.ApiService
import id.binar.fp.secondhand.domain.model.Notification
import id.binar.fp.secondhand.domain.repository.NotificationRepository
import id.binar.fp.secondhand.util.Result
import javax.inject.Inject

class NotificationRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : NotificationRepository {

    override fun getNotification(): LiveData<Result<List<Notification>>> = liveData {
        TODO("Not yet implemented")
    }

    override fun getNotificationById(id: Int): LiveData<Result<Notification>> = liveData {
        TODO("Not yet implemented")
    }

    override fun updateNotificationById(id: Int): LiveData<Result<Notification>> = liveData {
        TODO("Not yet implemented")
    }
}