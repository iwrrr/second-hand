package id.binar.fp.secondhand.data.source.network

import id.binar.fp.secondhand.data.source.network.response.*
import id.binar.fp.secondhand.util.Endpoints
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface ApiService {

    @FormUrlEncoded
    @POST(Endpoints.REGISTER)
    suspend fun register(
        @Field("full_name") fullName: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("phone_number") phoneNumber: String,
        @Field("city") city: String,
        @Field("address") address: String,
    ): UserDto

    @FormUrlEncoded
    @POST(Endpoints.LOGIN)
    suspend fun login(
        @Field("email") email: String?,
        @Field("password") password: String?,
    ): UserDto

    @GET(Endpoints.GET_USER)
    suspend fun getUser(): UserDto

    @PUT(Endpoints.UPDATE_USER)
    suspend fun updateUser(
        @Body body: RequestBody
    ): UserDto

    @POST(Endpoints.ADD_SELLER_PRODUCT)
    suspend fun addSellerProduct(
        @Body body: RequestBody
    ): ProductDto

    @GET(Endpoints.GET_SELLER_PRODUCT)
    suspend fun getSellerProduct(): List<ProductDto>

    @GET(Endpoints.GET_SELLER_PRODUCT_BY_ID)
    suspend fun getSellerProductById(
        @Path("id") id: Int
    ): ProductDto

    @PUT(Endpoints.UPDATE_SELLER_PRODUCT_BY_ID)
    suspend fun updateSellerProductById(
        @Path("id") id: Int,
        @Field("name") name: String,
        @Field("description") description: String,
        @Field("base_price") basePrice: String,
        @Field("category_ids") categoryIds: List<Int>,
        @Field("location") location: String,
        @Part("image") image: MultipartBody.Part,
    ): ProductDto

    @DELETE(Endpoints.DELETE_SELLER_PRODUCT_BY_ID)
    suspend fun deleteSellerProductById(
        @Path("id") id: Int
    ): MessageDto

    @GET(Endpoints.GET_SELLER_ORDER)
    suspend fun getSellerOrder(): List<SellerOrderDto>

    @GET(Endpoints.GET_SELLER_ORDER_BY_ID)
    suspend fun getSellerOrderById(
        @Path("id") id: Int
    ): SellerOrderDto

    @PATCH(Endpoints.UPDATE_SELLER_ORDER_BY_ID)
    suspend fun updateSellerOrderById(
        @Path("id") id: Int
    ) // TODO: Response Body Not Yet Implemented

    @GET(Endpoints.GET_SELLER_ORDER_BY_PRODUCT_ID)
    suspend fun getSellerOrderByProductId(
        @Path("id") id: Int
    ) // TODO: Response Body Not Yet Implemented

    @POST(Endpoints.ADD_CATEGORY)
    suspend fun addCategory(
        @Field("name") name: String
    ): CategoryDto

    @GET(Endpoints.GET_CATEGORY)
    suspend fun getCategory(): List<CategoryDto>

    @GET(Endpoints.GET_CATEGORY_BY_ID)
    suspend fun getCategoryById(
        @Path("id") id: Int
    ): CategoryDto

    @DELETE(Endpoints.DELETE_CATEGORY_BY_ID)
    suspend fun deleteCategoryById(
        @Path("id") id: Int
    ): Boolean

    @POST(Endpoints.ADD_BANNER)
    suspend fun addBanner(
        @Field("address") address: String,
        @Part("image") image: MultipartBody.Part,
    )

    @GET(Endpoints.GET_BANNER)
    suspend fun getBanner(): List<BannerDto>

    @GET(Endpoints.GET_BANNER_BY_ID)
    suspend fun getBannerById(
        @Path("id") id: Int
    )

    @DELETE(Endpoints.DELETE_BANNER_BY_ID)
    suspend fun deleteBannerById(
        @Path("id") id: Int
    )

    @GET(Endpoints.GET_BUYER_PRODUCT)
    suspend fun getBuyerProduct(
        @Query("category_id") categoryId: Int? = null,
        @Query("search") search: String? = null,
    ): List<ProductDto>

    @GET(Endpoints.GET_BUYER_PRODUCT_BY_ID)
    suspend fun getBuyerProductById(
        @Path("id") id: Int
    ): ProductDto

    @FormUrlEncoded
    @POST(Endpoints.ADD_BUYER_ORDER)
    suspend fun addBuyerOrder(
        @Field("product_id") productId: Int,
        @Field("bid_price") bidPrice: String,
    ): OrderDto

    @GET(Endpoints.GET_BUYER_ORDER)
    suspend fun getBuyerOrder(): List<OrderDto>

    @GET(Endpoints.GET_BUYER_ORDER_BY_ID)
    suspend fun getBuyerOrderById(
        @Path("id") id: Int
    ): OrderDto

    @PUT(Endpoints.UPDATE_BUYER_ORDER_BY_ID)
    suspend fun updateBuyerOrderById(
        @Path("id") id: Int,
        @Field("product_id") productId: Int,
        @Field("bid_price") bidPrice: Int,
    ): OrderDto

    @DELETE(Endpoints.DELETE_BUYER_ORDER_BY_ID)
    suspend fun deleteBuyerOrderById(
        @Path("id") id: Int
    ) // TODO: Response Body Not Yet Implemented

    @GET(Endpoints.GET_HISTORY)
    suspend fun getHistory(): List<HistoryDto>

    @GET(Endpoints.GET_HISTORY_BY_ID)
    suspend fun getHistoryById(
        @Path("id") id: Int
    ): HistoryDto

    @GET(Endpoints.GET_NOTIFICATION)
    suspend fun getNotification(): List<NotificationDto>

    @GET(Endpoints.GET_NOTIFICATION_BY_ID)
    suspend fun getNotificationById(
        @Path("id") id: Int
    ): NotificationDto

    @PATCH(Endpoints.UPDATE_NOTIFICATION_BY_ID)
    suspend fun updateNotificationById(
        @Path("id") id: Int
    ): NotificationDto
}