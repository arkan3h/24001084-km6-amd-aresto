package com.arkan.aresto.di

import android.content.SharedPreferences
import com.arkan.aresto.data.datasource.auth.AuthDataSource
import com.arkan.aresto.data.datasource.auth.FirebaseAuthDataSource
import com.arkan.aresto.data.datasource.cart.CartDataSource
import com.arkan.aresto.data.datasource.cart.CartDatabaseDataSource
import com.arkan.aresto.data.datasource.category.CategoryApiDataSource
import com.arkan.aresto.data.datasource.category.CategoryDataSource
import com.arkan.aresto.data.datasource.product.ProductApiDataSource
import com.arkan.aresto.data.datasource.product.ProductDataSource
import com.arkan.aresto.data.datasource.user.UserDataSource
import com.arkan.aresto.data.datasource.user.UserPreferenceDataSource
import com.arkan.aresto.data.repository.CartRepository
import com.arkan.aresto.data.repository.CartRepositoryImpl
import com.arkan.aresto.data.repository.CategoryRepository
import com.arkan.aresto.data.repository.CategoryRepositoryImpl
import com.arkan.aresto.data.repository.ProductRepository
import com.arkan.aresto.data.repository.ProductRepositoryImpl
import com.arkan.aresto.data.repository.UserRepository
import com.arkan.aresto.data.repository.UserRepositoryImpl
import com.arkan.aresto.data.source.firebase.FirebaseService
import com.arkan.aresto.data.source.local.database.AppDatabase
import com.arkan.aresto.data.source.local.database.dao.CartDao
import com.arkan.aresto.data.source.network.services.ArestoApiService
import com.arkan.aresto.data.source.pref.UserPreference
import com.arkan.aresto.data.source.pref.UserPreferenceImpl
import com.arkan.aresto.presentation.cart.CartViewModel
import com.arkan.aresto.presentation.checkout.CheckoutViewModel
import com.arkan.aresto.presentation.detailproduct.DetailProductViewModel
import com.arkan.aresto.presentation.home.HomeViewModel
import com.arkan.aresto.presentation.login.LoginViewModel
import com.arkan.aresto.presentation.profile.ProfileViewModel
import com.arkan.aresto.presentation.register.RegisterViewModel
import com.arkan.aresto.utils.SharedPreferenceUtils
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

object AppModules {
    private val apiModule =
        module {
            single<ArestoApiService> { ArestoApiService.invoke() }
        }

    private val firebaseModule =
        module {
            single<FirebaseService> { FirebaseService.getInstance() }
        }

    private val localModule =
        module {
            single<AppDatabase> { AppDatabase.getInstance(androidContext()) }
            single<CartDao> { get<AppDatabase>().cartDao() }
            single<SharedPreferences> {
                SharedPreferenceUtils.createPreference(
                    androidContext(),
                    UserPreferenceImpl.PREF_NAME,
                )
            }
            single<UserPreference> { UserPreferenceImpl(get()) }
        }

    private val dataSource =
        module {
            single<CartDataSource> { CartDatabaseDataSource(get()) }
            single<CategoryDataSource> { CategoryApiDataSource(get()) }
            single<ProductDataSource> { ProductApiDataSource(get()) }
            single<UserDataSource> { UserPreferenceDataSource(get()) }
            single<AuthDataSource> { FirebaseAuthDataSource(get()) }
        }

    private val repository =
        module {
            single<CartRepository> { CartRepositoryImpl(get()) }
            single<CategoryRepository> { CategoryRepositoryImpl(get()) }
            single<ProductRepository> { ProductRepositoryImpl(get()) }
            single<UserRepository> { UserRepositoryImpl(get(), get()) }
        }

    private val viewModelModule =
        module {
            viewModelOf(::HomeViewModel)
            viewModelOf(::CartViewModel)
            viewModelOf(::ProfileViewModel)
            viewModel { params ->
                DetailProductViewModel(
                    extras = params.get(),
                    cartRepository = get(),
                )
            }
            viewModelOf(::CheckoutViewModel)
            viewModelOf(::LoginViewModel)
            viewModelOf(::RegisterViewModel)
        }

    val modules =
        listOf(
            apiModule,
            firebaseModule,
            localModule,
            dataSource,
            repository,
            viewModelModule,
        )
}
