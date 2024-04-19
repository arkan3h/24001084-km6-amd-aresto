package com.arkan.aresto.presentation.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.arkan.aresto.data.model.Profile
import com.arkan.aresto.data.repository.UserRepository

class ProfileViewModel(private val repo: UserRepository) : ViewModel() {
    val profileData = MutableLiveData(
        Profile(
            name = "Arkan Mahardika",
            username = "arkan_3h",
            email = "arkan.mahardika.1@gmail.com",
            profileImg = "https://github.com/arkan3h/Public-Image/blob/main/Tak%20berjudul6.png?raw=true"
        )
    )

    val isEditMode = MutableLiveData(false)

    fun changeEditMode() {
        val currentValue = isEditMode.value ?: false
        isEditMode.postValue(!currentValue)
    }

    fun isUserLoggedIn() = repo.isLoggedIn()

    fun doLogout() {
        repo.doLogout()
    }
}