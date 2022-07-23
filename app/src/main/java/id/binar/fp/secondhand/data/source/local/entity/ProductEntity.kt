package id.binar.fp.secondhand.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import id.binar.fp.secondhand.domain.model.Product

@Entity(tableName = "products")
data class ProductEntity(

    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int = 0,

    @ColumnInfo(name = "name")
    val name: String? = null,

    @ColumnInfo(name = "base_price")
    val basePrice: Int = 0,

    @ColumnInfo(name = "description")
    val description: String? = null,

    @ColumnInfo(name = "image_url")
    val imageUrl: String? = null,

    @ColumnInfo(name = "image_name")
    val imageName: String? = null,

    @ColumnInfo(name = "location")
    val location: String? = null,

    @ColumnInfo(name = "user_id")
    val userId: Int = 0,

    @Expose
    @ColumnInfo(name = "user")
    val user: UserEntity? = null,

    @ColumnInfo(name = "status")
    val status: String? = null,

    @Expose
    @ColumnInfo(name = "categories")
    val categories: List<CategoryEntity>? = null,

    @ColumnInfo(name = "created_at")
    val createdAt: String? = null,

    @ColumnInfo(name = "updated_at")
    val updatedAt: String? = null,
) {

    fun toDomain(): Product {
        val categories = categories?.map { it.toDomain() }
        return Product(
            id,
            name,
            basePrice,
            description,
            imageUrl,
            imageName,
            location,
            userId,
            user?.toDomain(),
            status,
            categories,
            createdAt,
            updatedAt
        )
    }
}