package id.binar.fp.secondhand.util.dummy

object DataDummy {

    fun setDummyProducts(): ArrayList<Product> {

        val product1 = Product(
            1,
            "Jam Tangan Casio",
            "Rp210.000",
            "Penawaran Produk",
            "20 Apr, 14:04",
            "Ditawar Rp150.000"
        )
        val product2 = Product(
            2,
            "Jam Tangan Casio",
            "Rp220.000",
            "Penawaran Produk",
            "20 Apr, 14:04",
            "Ditawar Rp150.000"
        )
        val product3 = Product(
            3,
            "Jam Tangan Casio",
            "Rp230.000",
            "Penawaran Produk",
            "20 Apr, 14:04",
            "Ditawar Rp150.000"
        )
        val product4 = Product(
            4,
            "Jam Tangan Casio",
            "Rp230.000",
            "Penawaran Produk",
            "20 Apr, 14:04",
            "Ditawar Rp150.000"
        )
        val product5 = Product(
            5,
            "Jam Tangan Casio",
            "Rp230.000",
            "Penawaran Produk",
            "20 Apr, 14:04",
            "Ditawar Rp150.000"
        )
        val product6 = Product(
            6,
            "Jam Tangan Casio",
            "Rp230.000",
            "Penawaran Produk",
            "20 Apr, 14:04",
            "Ditawar Rp150.000"
        )
        val product7 = Product(
            7,
            "Jam Tangan Casio",
            "Rp230.000",
            "Penawaran Produk",
            "20 Apr, 14:04",
            "Ditawar Rp150.000"
        )
        val product8 = Product(
            8,
            "Jam Tangan Casio",
            "Rp230.000",
            "Penawaran Produk",
            "20 Apr, 14:04",
            "Ditawar Rp150.000"
        )

        val products = ArrayList<Product>()

        products.add(product1)
        products.add(product2)
        products.add(product3)
        products.add(product4)
        products.add(product5)
        products.add(product6)
        products.add(product7)
        products.add(product8)

        return products
    }

    fun setCategories(): List<Category> {
        val category1 = Category(
            1,
            "semua"
        )
        val category2 = Category(
            2,
            "elektronik"
        )
        val category3 = Category(
            3,
            "kendaraan"
        )
        val category4 = Category(
            4,
            "kendaraan"
        )
        val category5 = Category(
            5,
            "pakaian"
        )
        val category6 = Category(
            6,
            "pakaian"
        )
        val category7 = Category(
            7,
            "pakaian"
        )
        val category8 = Category(
            8,
            "pakaian"
        )
        val category9 = Category(
            9,
            "pakaian"
        )

        val categories = ArrayList<Category>()

        categories.add(category1)
        categories.add(category2)
        categories.add(category3)
        categories.add(category4)
        categories.add(category5)
        categories.add(category6)
        categories.add(category7)
        categories.add(category8)
        categories.add(category9)

        return categories
    }
}