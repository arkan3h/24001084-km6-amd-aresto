package com.arkan.aresto.presentation.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.arkan.aresto.data.model.Profile

class ProfileViewModel: ViewModel() {
    val profileData = MutableLiveData(
        Profile(
            name = "Arkan Mahardika",
            username = "arkan_3h",
            email = "arkan.mahardika.1@gmail.com",
            profileImg = "https://avatars.githubusercontent.com/u/114378553?v=4"
        )
    )

    val isEditMode = MutableLiveData(false)

    fun changeEditMode() {
        val currentValue = isEditMode.value ?: false
        isEditMode.postValue(!currentValue)
    }
}