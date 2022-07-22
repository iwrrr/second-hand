package id.binar.fp.secondhand.util

object Endpoints {

    // Auth
    const val REGISTER = "auth/register"
    const val LOGIN = "auth/login"
    const val GET_USER = "auth/user"
    const val UPDATE_USER = "auth/user"
    const val CHANGE_PASSWORD = "auth/change-password"

    // Seller
    const val ADD_BANNER = "seller/banner"
    const val GET_BANNER = "seller/banner"
    const val GET_BANNER_BY_ID = "seller/banner/{id}"
    const val DELETE_BANNER_BY_ID = "seller/banner/{id}"

    const val ADD_CATEGORY = "seller/category"
    const val GET_CATEGORY = "seller/category"
    const val GET_CATEGORY_BY_ID = "seller/category/{id}"
    const val DELETE_CATEGORY_BY_ID = "seller/category/{id}"

    const val ADD_SELLER_PRODUCT = "seller/product"
    const val GET_SELLER_PRODUCT = "seller/product"
    const val GET_SELLER_PRODUCT_BY_ID = "seller/product/{id}"
    const val UPDATE_SELLER_PRODUCT_BY_ID = "seller/product/{id}"
    const val DELETE_SELLER_PRODUCT_BY_ID = "seller/product/{id}"

    const val GET_SELLER_ORDER = "seller/order"
    const val GET_SELLER_ORDER_BY_ID = "seller/order/{id}"
    const val UPDATE_SELLER_ORDER_BY_ID = "seller/order/{id}"
    const val GET_SELLER_ORDER_BY_PRODUCT_ID = "seller/order/product/{id}"

    // Buyer
    const val GET_BUYER_PRODUCT = "buyer/product"
    const val GET_BUYER_PRODUCT_BY_ID = "buyer/product/{id}"

    const val ADD_BUYER_ORDER = "buyer/order"
    const val GET_BUYER_ORDER = "buyer/order"
    const val GET_BUYER_ORDER_BY_ID = "buyer/order/{id}"
    const val UPDATE_BUYER_ORDER_BY_ID = "buyer/order/{id}"
    const val DELETE_BUYER_ORDER_BY_ID = "buyer/order/{id}"

    // History
    const val GET_HISTORY = "history"
    const val GET_HISTORY_BY_ID = "history/{id}"

    // Notification
    const val GET_NOTIFICATION = "notification"
    const val GET_NOTIFICATION_BY_ID = "notification/{id}"
    const val UPDATE_NOTIFICATION_BY_ID = "notification/{id}"
}