package id.binar.fp.secondhand.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import id.binar.fp.secondhand.domain.model.Banner

@Entity(tableName = "banner")
data class BannerEntity(

    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int = 0,

    @ColumnInfo(name = "name")
    val name: String? = null,

    @ColumnInfo(name = "image_url")
    val imageUrl: String,

    @ColumnInfo(name = "createdAt")
    val createdAt: String? = null,

    @ColumnInfo(name = "updatedAt")
    val updatedAt: String? = null
) {

    fun toDomain(): Banner {
        return Banner(
            id, name, imageUrl, createdAt, updatedAt
        )
    }
}
