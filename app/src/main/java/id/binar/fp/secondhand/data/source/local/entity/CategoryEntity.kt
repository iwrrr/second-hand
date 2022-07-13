package id.binar.fp.secondhand.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import id.binar.fp.secondhand.domain.model.Category

@Entity(tableName = "categories")
data class CategoryEntity(

    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "createdAt")
    val createdAt: String? = null,

    @ColumnInfo(name = "updatedAt")
    val updatedAt: String? = null
) {

    fun toDomain(): Category {
        return Category(
            id,
            name,
            createdAt,
            updatedAt
        )
    }
}
