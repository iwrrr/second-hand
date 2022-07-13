package id.binar.fp.secondhand.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import id.binar.fp.secondhand.domain.model.History

@Entity(tableName = "histories")
data class HistoryEntity(

    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "product_name")
    val productName: String? = null,

    @ColumnInfo(name = "price")
    val price: Int? = null,

    @ColumnInfo(name = "category")
    val category: String? = null,

    @ColumnInfo(name = "transaction_date")
    val transactionDate: String? = null,

    @ColumnInfo(name = "status")
    val status: String? = null,

    @ColumnInfo(name = "user_id")
    val userId: Int? = null,

    @ColumnInfo(name = "image_url")
    val imageUrl: String? = null,

    @ColumnInfo(name = "createdAt")
    val createdAt: String? = null,

    @ColumnInfo(name = "updatedAt")
    val updatedAt: String? = null
) {

    fun toDomain(): History {
        return History(
            id,
            productName,
            price,
            category,
            transactionDate,
            status,
            userId,
            imageUrl,
            createdAt,
            updatedAt
        )
    }
}
