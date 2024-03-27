package com.arkan.aresto.data.datasource

import com.arkan.aresto.data.model.Product

class DummyProductDataSource : ProductDataSource {
    override fun getProductList(): List<Product> {
        return mutableListOf(
            Product(
                imgUrl = "https://raw.githubusercontent.com/arkan3h/Aresto-assets/main/product_img/img_grilled_chicken.webp",
                name = "Ayam Bakar",
                price = 50000.00,
                desc = "Ayam Bakar adalah hidangan yang terdiri dari potongan ayam yang telah dibumbui dengan rempah-rempah khas dan kemudian dipanggang hingga matang dan berkulit kecokelatan. Proses pemanggangan ini memberikan aroma yang khas dan meresapkan rasa rempah yang lezat ke dalam daging ayam. Hidangan ini disajikan dengan nasi hangat, sambal, dan lalapan seperti mentimun atau tomat.",
                address = "Jl. BSD Green Office PArk Jl. BSD Grand Boulevard, Sampora, BSD, Kabupaten Tangerang, Banten 15345",
                addressUrl = "https://maps.app.goo.gl/h4wQKqaBuXzftGK77"
            ),
            Product(
                imgUrl = "https://raw.githubusercontent.com/arkan3h/Aresto-assets/main/product_img/img_fried_chicken.webp",
                name = "Ayam Goreng",
                price = 40000.00,
                desc = "Ayam Goreng adalah hidangan yang terdiri dari potongan-potongan ayam yang dibumbui dengan rempah-rempah khas, kemudian digoreng hingga kecokelatan dan renyah. Hidangan ini disajikan dengan nasi putih, sambal, irisan mentimun, dan tomat untuk memberikan sensasi rasa yang gurih dan lezat.",
                address = "Jl. BSD Green Office PArk Jl. BSD Grand Boulevard, Sampora, BSD, Kabupaten Tangerang, Banten 15345",
                addressUrl = "https://maps.app.goo.gl/h4wQKqaBuXzftGK77"
            ),
            Product(
                imgUrl = "https://raw.githubusercontent.com/arkan3h/Aresto-assets/main/product_img/img_geprek_chicken.webp",
                name = "Ayam Geprek",
                price = 40000.00,
                desc = "Ayam Geprek adalah hidangan yang terdiri dari potongan ayam goreng yang kemudian diulek atau digeprek bersama sambal pedas. Proses penggeprekan dengan sambal pedas memberikan sensasi pedas dan gurih yang lezat. Hidangan ini disajikan dengan nasi hangat, irisan mentimun, dan tomat sebagai pelengkapnya.",
                address = "Jl. BSD Green Office PArk Jl. BSD Grand Boulevard, Sampora, BSD, Kabupaten Tangerang, Banten 15345",
                addressUrl = "https://maps.app.goo.gl/h4wQKqaBuXzftGK77"
            ),
            Product(
                imgUrl = "https://raw.githubusercontent.com/arkan3h/Aresto-assets/main/product_img/img_chicken_guts_satay.webp",
                name = "Sate Usus Ayam",
                price = 5000.00,
                desc = "Sate Usus Ayam adalah hidangan yang terdiri dari potongan-potongan usus ayam yang ditusuk menggunakan tusuk sate, kemudian dipanggang atau dibakar hingga matang. Usus ayam tersebut dibumbui dengan rempah-rempah sebelum dipanggang untuk memberikan cita rasa yang khas. ",
                address = "Jl. BSD Green Office PArk Jl. BSD Grand Boulevard, Sampora, BSD, Kabupaten Tangerang, Banten 15345",
                addressUrl = "https://maps.app.goo.gl/h4wQKqaBuXzftGK77"
            ),
            Product(
                imgUrl = "https://raw.githubusercontent.com/arkan3h/Aresto-assets/main/product_img/img_fried_rice.webp",
                name = "Nasi Goreng",
                price = 20000.00,
                desc = "Nasi Goreng adalah hidangan nasi yang digoreng bersama dengan bumbu-bumbu, seperti bawang putih, bawang merah, kecap manis, dan bumbu lainnya. Hidangan ini juga dicampur dengan potongan daging ayam, udang, wortel, dan kacang polong. Nasi Goreng disajikan dengan telur mata sapi di atasnya dan acar sebagai pelengkapnya. ",
                address = "Jl. BSD Green Office PArk Jl. BSD Grand Boulevard, Sampora, BSD, Kabupaten Tangerang, Banten 15345",
                addressUrl = "https://maps.app.goo.gl/h4wQKqaBuXzftGK77"
            ),
            Product(
                imgUrl = "https://raw.githubusercontent.com/arkan3h/Aresto-assets/main/product_img/img_fried_noodle.webp",
                name = "Mie Goreng",
                price = 15000.00,
                desc = "Mie Goreng adalah hidangan mie yang dimasak dengan cara digoreng bersama dengan berbagai bumbu, seperti bawang putih, bawang merah, saus kecap manis, saus sambal, dan dicampur dengan potongan daging, udang, dan sayuran. Mi Goreng disajikan dengan irisan telur mata sapi di atasnya atau telur rebus sebagai tambahan protein. ",
                address = "Jl. BSD Green Office PArk Jl. BSD Grand Boulevard, Sampora, BSD, Kabupaten Tangerang, Banten 15345",
                addressUrl = "https://maps.app.goo.gl/h4wQKqaBuXzftGK77"
            ),
        )
    }
}