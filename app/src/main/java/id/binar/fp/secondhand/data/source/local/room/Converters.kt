package id.binar.fp.secondhand.data.source.local.room

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import id.binar.fp.secondhand.data.source.local.entity.CategoryEntity
import id.binar.fp.secondhand.data.source.local.entity.ProductEntity
import id.binar.fp.secondhand.data.source.local.entity.UserEntity

class Converters {

    @TypeConverter
    fun fromProductList(countryLang: List<ProductEntity>): String {
        val type = object : TypeToken<List<ProductEntity>>() {}.type
        return Gson().toJson(countryLang, type)
    }

    @TypeConverter
    fun toProductList(countryLangString: String): List<ProductEntity> {
        val type = object : TypeToken<List<ProductEntity>>() {}.type
        return Gson().fromJson(countryLangString, type)
    }

    @TypeConverter
    fun fromCategoryList(countryLang: List<CategoryEntity>): String {
        val type = object : TypeToken<List<CategoryEntity>>() {}.type
        return Gson().toJson(countryLang, type)
    }

    @TypeConverter
    fun toCategoryList(countryLangString: String): List<CategoryEntity> {
        val type = object : TypeToken<List<CategoryEntity>>() {}.type
        return Gson().fromJson(countryLangString, type)
    }

    @TypeConverter
    fun fromProduct(countryLang: ProductEntity): String {
        val type = object : TypeToken<ProductEntity>() {}.type
        return Gson().toJson(countryLang, type)
    }

    @TypeConverter
    fun toProduct(countryLangString: String): ProductEntity {
        val type = object : TypeToken<ProductEntity>() {}.type
        return Gson().fromJson(countryLangString, type)
    }

    @TypeConverter
    fun fromUser(countryLang: UserEntity): String {
        val type = object : TypeToken<UserEntity>() {}.type
        return Gson().toJson(countryLang, type)
    }

    @TypeConverter
    fun toUser(countryLangString: String): UserEntity {
        val type = object : TypeToken<UserEntity>() {}.type
        return Gson().fromJson(countryLangString, type)
    }
}