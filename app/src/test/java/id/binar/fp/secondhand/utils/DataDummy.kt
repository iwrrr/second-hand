package id.binar.fp.secondhand.utils

import id.binar.fp.secondhand.data.source.network.response.MessageDto
import id.binar.fp.secondhand.domain.model.*

object DataDummy {
    fun generateUsers(): List<User> {
        val users = ArrayList<User>()
        for (i in 0..3) {
            val user = User(
                id = 1,
                fullName = "testName",
                email = "test@example.com",
                password = "123456",
                city = "Jakarta",
                address = "jl. Jakarta"
            )
            users.add(user)
        }
        return users
    }

    fun generateUserId(): User {
        return User(
            id = 1,
            fullName = "testName",
            email = "test@example.com",
            password = "123456",
            city = "Jakarta",
            address = "jl. Jakarta"
        )
    }

    fun generateHistories(): List<History> {
        val histories = ArrayList<History>()
        for (i in 0..3) {
            val history = History(
                id = 1,
                productName = "TestingName",
                price = 100000,
                category = "Kategori"
            )
            histories.add(history)
        }
        return histories
    }

    fun generateHistoriesId(): History {
        return History(
            id = 1,
            productName = "TestingName",
            price = 100000,
            category = "Kategori"
        )
    }

    fun generateBanners(): List<Banner> {
        val banners = ArrayList<Banner>()
        for (i in 0..3) {
            val banner = Banner(
                id = 1,
                name = "banner",
                imageUrl = "url"
            )
            banners.add(banner)
        }
        return banners
    }

    fun generateCategories(): List<Category> {
        val categories = ArrayList<Category>()
        for (i in 0..3) {
            val category = Category(
                id = 1,
                name = "kategori"
            )
            categories.add(category)
        }
        return categories
    }

    fun generateProducts(): List<Product> {
        val products = ArrayList<Product>()
        for (i in 0..3) {
            val product = Product(
                id = 1,
                name = "name",
                basePrice = 100000,
                description = "description",
                categories = listOf(),
                location = "lokasi",
                imageUrl = "url",
            )
            products.add(product)
        }
        return products
    }

    fun generateNotif(): List<Notification> {
        val notifs = ArrayList<Notification>()
        for (i in 0..3) {
            val notif = Notification(
                id = 1,
                productId = 1,
                productName = "name",
                basePrice = "100000",
                bidPrice = 10000,
                imageUrl = "string"
            )
            notifs.add(notif)
        }
        return notifs
    }

    fun generateNotifId(): Notification {
        return Notification(
            id = 1,
            productId = 1,
            productName = "name",
            basePrice = "100000",
            bidPrice = 10000,
            imageUrl = "string"
        )
    }

    fun generateOrders(): List<BuyerOrder> {
        val orders = ArrayList<BuyerOrder>()
        for (i in 0..3) {
            val order = BuyerOrder(
                id = 1,
                productId = 1,
            )
            orders.add(order)
        }
        return orders
    }

    fun generateAddProducts(): Product {
        return Product(
            id = 1,
            name = "name",
            basePrice = 100000,
            description = "description",
            categories = listOf(),
            location = "lokasi",
            imageUrl = "url"
        )
    }

    fun generateAddBuyerOrder(): Order {
        return Order(
            id = 1,
            productId = 1,
            price = 1000
        )
    }

    fun generateDeleteSeller(): MessageDto {
        return MessageDto()
    }

    fun generateSellerOrders(): List<SellerOrder> {
        val orders = ArrayList<SellerOrder>()
        for (i in 0..3) {
            val order = SellerOrder(
                id = 1,
                productId = 1
            )
            orders.add(order)
        }
        return orders
    }

    fun generateSellerOrdersId(): SellerOrder {
        return SellerOrder(
            id = 1,
            productId = 1,
            status = "status"
        )
    }
}