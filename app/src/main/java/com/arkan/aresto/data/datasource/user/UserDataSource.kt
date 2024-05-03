package com.arkan.aresto.data.datasource.user

import com.arkan.aresto.data.source.pref.UserPreference

interface UserDataSource {
    fun isUsingGrid(): Boolean

    fun setUsingGridMode(isUsingGrid: Boolean)
}

class UserPreferenceDataSource(private val userPreference: UserPreference) : UserDataSource {
    override fun isUsingGrid(): Boolean {
        return userPreference.isUsingGrid()
    }

    override fun setUsingGridMode(isUsingGrid: Boolean) {
        userPreference.setUsingGridMode(isUsingGrid)
    }
}
