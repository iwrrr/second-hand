package id.binar.fp.secondhand.domain.model

import android.os.Parcelable
import id.binar.fp.secondhand.data.source.local.entity.CategoryEntity
import id.binar.fp.secondhand.data.source.network.response.CategoryDto
import kotlinx.parcelize.Parcelize

@Parcelize
data class Category(
    val id: Int,
    val name: String,
    val createdAt: String? = null,
    val updatedAt: String? = null
) : Parcelable, BaseType {

    fun toDto(): CategoryDto {
        return CategoryDto(
            id,
            name,
            createdAt,
            updatedAt
        )
    }

    fun toEntity(): CategoryEntity {
        return CategoryEntity(
            id,
            name,
            createdAt,
            updatedAt
        )
    }
}
