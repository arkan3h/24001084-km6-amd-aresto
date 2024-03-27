package com.arkan.aresto.data.datasource

import com.arkan.aresto.data.model.Category

class DummyCategoryDataSource : CategoryDataSource {
    override fun getCategoryList(): List<Category> {
        return mutableListOf(
            Category(
                imgUrl = "https://github.com/arkan3h/Aresto-assets/blob/main/category_img/img_category_rice.png?raw=true",
                name = "Nasi"
            ),
            Category(
                imgUrl = "https://github.com/arkan3h/Aresto-assets/blob/main/category_img/img_category_noodle.png?raw=true",
                name = "Mie"
            ),
            Category(
                imgUrl = "https://github.com/arkan3h/Aresto-assets/blob/main/category_img/img_category_snack.png?raw=true",
                name = "Snack"
            ),
            Category(
                imgUrl = "https://github.com/arkan3h/Aresto-assets/blob/main/category_img/img_category_drink.png?raw=true",
                name = "Minuman"
            ),
        )
    }
}