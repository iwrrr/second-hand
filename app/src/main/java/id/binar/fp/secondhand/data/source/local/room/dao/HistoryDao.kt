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

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHistory(history: HistoryEntity)

    @Query("SELECT * FROM histories ORDER BY transaction_date DESC")
    suspend fun getHistory(): List<HistoryEntity>

    @Query("SELECT * FROM histories WHERE id = :id")
    suspend fun getHistoryById(id: Int): HistoryEntity?

    @Query("DELETE FROM histories")
    suspend fun deleteHistories()
}