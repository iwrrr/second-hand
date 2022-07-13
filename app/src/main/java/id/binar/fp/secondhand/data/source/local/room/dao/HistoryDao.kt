package id.binar.fp.secondhand.data.source.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import id.binar.fp.secondhand.data.source.local.entity.HistoryEntity

@Dao
interface HistoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHistories(histories: List<HistoryEntity>)

    @Query("SELECT * FROM histories WHERE user_id = :userId")
    suspend fun getHistory(userId: Int): List<HistoryEntity>

    @Query("SELECT * FROM histories WHERE id = :id AND user_id = :userId")
    suspend fun getHistoryById(id: Int, userId: Int): HistoryEntity

    @Query("DELETE FROM histories")
    suspend fun deleteHistories()
}