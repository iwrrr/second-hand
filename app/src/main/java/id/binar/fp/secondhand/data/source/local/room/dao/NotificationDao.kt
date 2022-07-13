package id.binar.fp.secondhand.data.source.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import id.binar.fp.secondhand.data.source.local.entity.NotificationEntity

@Dao
interface NotificationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNotifications(notifications: List<NotificationEntity>)

    @Query("SELECT * FROM notifications")
    suspend fun getNotification(): List<NotificationEntity>

    @Query("DELETE FROM notifications")
    suspend fun deleteNotifications()
}