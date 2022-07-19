package id.binar.fp.secondhand.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import id.binar.fp.secondhand.domain.model.User

@Entity(tableName = "users")
data class UserEntity(

    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int? = null,

    @ColumnInfo(name = "full_name")
    val fullName: String? = null,

    @ColumnInfo(name = "email")
    val email: String? = null,

    @ColumnInfo(name = "password")
    val password: String? = null,

    @ColumnInfo(name = "phone_number")
    val phoneNumber: String? = null,

    @ColumnInfo(name = "city")
    val city: String? = null,

    @ColumnInfo(name = "address")
    val address: String? = null,

    @ColumnInfo(name = "image_url")
    val imageUrl: String? = null,

    @ColumnInfo(name = "created_at")
    val createdAt: String? = null,

    @ColumnInfo(name = "updated_at")
    val updatedAt: String? = null,

    @ColumnInfo(name = "access_token")
    val accessToken: String? = null,

    @field:SerializedName("error")
    val error: Boolean = false,

    @field:SerializedName("message")
    val message: String? = null,
) {

    fun toDomain(): User {
        return User(
            id,
            fullName,
            email,
            password,
            phoneNumber,
            city,
            address,
            imageUrl,
            createdAt,
            updatedAt,
            accessToken,
            error,
            message
        )
    }
}