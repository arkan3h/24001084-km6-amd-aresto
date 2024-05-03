package com.arkan.aresto.data.source.network.services

import com.arkan.aresto.BuildConfig
import com.arkan.aresto.data.source.network.model.category.CategoriesResponse
import com.arkan.aresto.data.source.network.model.checkout.CheckoutRequestPayload
import com.arkan.aresto.data.source.network.model.checkout.CheckoutResponse
import com.arkan.aresto.data.source.network.model.product.ProductResponse
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface ArestoApiService {
    @GET("category")
    suspend fun getCategories(): CategoriesResponse

    @GET("listmenu")
    suspend fun getProducts(
        @Query("c") category: String? = null,
    ): ProductResponse

    @POST("order")
    suspend fun createOrder(
        @Body payload: CheckoutRequestPayload,
    ): CheckoutResponse

    companion object {
        @JvmStatic
        operator fun invoke(): ArestoApiService {
            val okHttpClient =
                OkHttpClient.Builder()
                    .connectTimeout(120, TimeUnit.SECONDS)
                    .readTimeout(120, TimeUnit.SECONDS)
                    .build()
            val retrofit =
                Retrofit.Builder()
                    .baseUrl(BuildConfig.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build()
            return retrofit.create(ArestoApiService::class.java)
        }
    }
}
