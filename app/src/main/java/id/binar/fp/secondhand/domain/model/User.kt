package id.binar.fp.secondhand.domain.model

import android.os.Parcelable
import id.binar.fp.secondhand.data.source.local.entity.UserEntity
import id.binar.fp.secondhand.data.source.network.response.UserDto
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val id: Int? = null,
    val fullName: String? = null,
    val email: String? = null,
    val password: String? = null,
    val phoneNumber: String? = null,
    val city: String? = null,
    val address: String? = null,
    val imageUrl: String? = null,
    val createdAt: String? = null,
    val updatedAt: String? = null,
    val accessToken: String? = null,
    val error: Boolean = false,
    val message: String? = null,
) : Parcelable, BaseType {

    fun toDto(): UserDto {
        return UserDto(
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

    fun toEntity(): UserEntity {
        return UserEntity(
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
