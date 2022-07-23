package id.binar.fp.secondhand.sources.remote.service

import id.binar.fp.secondhand.data.source.network.ApiService
import id.binar.fp.secondhand.data.source.network.response.*
import id.binar.fp.secondhand.utils.DataDummy
import okhttp3.MultipartBody
import okhttp3.RequestBody

class FakeApiService : ApiService {

    private val dummyCategories = DataDummy.generateCategories()
    private val dummyHistory = DataDummy.generateHistories()
    private val dummyHistoryId = DataDummy.generateHistoriesId()
    private val dummyNotif = DataDummy.generateNotif()
    override suspend fun register(
        fullName: String,
        email: String,
        password: String,
        phoneNumber: String,
        city: String,
        address: String
    ): UserDto {
        TODO("Not yet implemented")
    }

    override suspend fun login(email: String?, password: String?): UserDto {
        TODO("Not yet implemented")
    }

    override suspend fun getUser(): UserDto {
        TODO("Not yet implemented")
    }

    override suspend fun updateUser(body: RequestBody): UserDto {
        TODO("Not yet implemented")
    }

    override suspend fun changePassword(body: RequestBody): MessageDto {
        TODO("Not yet implemented")
    }

    override suspend fun addSellerProduct(body: RequestBody): ProductDto {
        TODO("Not yet implemented")
    }

    override suspend fun getSellerProduct(): List<ProductDto> {
        TODO("Not yet implemented")
    }

    override suspend fun getSellerProductById(id: Int): ProductDto {
        TODO("Not yet implemented")
    }

    override suspend fun updateSellerProductById(id: Int, body: RequestBody): ProductDto {
        TODO("Not yet implemented")
    }

    override suspend fun updateSellerProductById(id: Int, status: String): ProductDto {
        TODO("Not yet implemented")
    }

    override suspend fun deleteSellerProductById(id: Int): MessageDto {
        TODO("Not yet implemented")
    }

    override suspend fun getSellerOrder(): List<SellerOrderDto> {
        TODO("Not yet implemented")
    }

    override suspend fun getSellerOrderById(id: Int): SellerOrderDto {
        TODO("Not yet implemented")
    }

    override suspend fun updateSellerOrderById(id: Int, status: String): SellerOrderDto {
        TODO("Not yet implemented")
    }

    override suspend fun getSellerOrderByProductId(id: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun addCategory(name: String): CategoryDto {
        TODO("Not yet implemented")
    }

    override suspend fun getCategory(): List<CategoryDto> {
        return dummyCategories.map {
            CategoryDto(
                id = it.id,
                name = it.name,
                createdAt = it.createdAt,
                updatedAt = it.updatedAt,
            )
        }
    }

    override suspend fun getCategoryById(id: Int): CategoryDto {
        TODO("Not yet implemented")
    }

    override suspend fun deleteCategoryById(id: Int): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun addBanner(address: String, image: MultipartBody.Part) {
        TODO("Not yet implemented")
    }

    override suspend fun getBanner(): List<BannerDto> {
        TODO("Not yet implemented")
    }

    override suspend fun getBannerById(id: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteBannerById(id: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun getBuyerProduct(categoryId: Int?, search: String?): List<ProductDto> {
        TODO("Not yet implemented")
    }

    override suspend fun getBuyerProductById(id: Int): ProductDto {
        TODO("Not yet implemented")
    }

    override suspend fun addBuyerOrder(productId: Int, bidPrice: String): OrderDto {
        TODO("Not yet implemented")
    }

    override suspend fun getBuyerOrder(): List<BuyerOrderDto> {
        TODO("Not yet implemented")
    }

    override suspend fun getBuyerOrderById(id: Int): BuyerOrderDto {
        TODO("Not yet implemented")
    }

    override suspend fun updateBuyerOrderById(
        id: Int,
        productId: Int,
        bidPrice: Int
    ): BuyerOrderDto {
        TODO("Not yet implemented")
    }

    override suspend fun deleteBuyerOrderById(id: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun getHistory(): List<HistoryDto> {
        return dummyHistory.map {
            HistoryDto(
                id = it.id,
                productName = it.productName,
                price = it.price,
                category = it.category
            )
        }
    }

    override suspend fun getHistoryById(id: Int): HistoryDto {
        return HistoryDto(
            id = dummyHistoryId.id,
            productName = dummyHistoryId.productName,
            price = dummyHistoryId.price,
            category = dummyHistoryId.category
        )
    }

    override suspend fun getNotification(): List<NotificationDto> {
        return dummyNotif.map {
            NotificationDto(
                id = it.id,
                productId = it.productId,
                productName = "name",
                basePrice = "100000",
                bidPrice = 10000,
                imageUrl = "string"
            )
        }
    }

    override suspend fun getNotificationById(id: Int): NotificationDto {
        return NotificationDto(
            id = 1,
            productId = 1,
            productName = "name",
            basePrice = "100000",
            bidPrice = 10000,
            imageUrl = "string",
        )
    }

    override suspend fun updateNotificationById(id: Int): NotificationDto {
        return NotificationDto(
            id = 1,
            productId = 1,
            productName = "name",
            basePrice = "100000",
            bidPrice = 10000,
            imageUrl = "string",
        )
    }
}