package com.arkan.aresto.presentation.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.arkan.aresto.data.repository.UserRepository
import kotlinx.coroutines.Dispatchers

class ProfileViewModel(private val repo: UserRepository) : ViewModel() {
    val isEditMode = MutableLiveData(false)

    fun changeEditMode() {
        val currentValue = isEditMode.value ?: false
        isEditMode.postValue(!currentValue)
    }

    fun getCurrentUser() = repo.getCurrentUser()

    fun updateProfile(fullName: String) = repo.updateProfile(fullName).asLiveData(Dispatchers.IO)

    fun createChangePwdRequest() = repo.requestChangePasswordByEmail()

    fun isUserLoggedIn() = repo.isLoggedIn()

    fun doLogout() = repo.doLogout()
}
