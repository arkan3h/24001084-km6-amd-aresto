package com.arkan.aresto.utils

import android.content.Context
import com.arkan.aresto.utils.SharedPreferenceUtils.set

interface UserPreference {
    fun isUsingGrid(): Boolean
    fun setUsingGridMode(isUsingGrid: Boolean)
}

class UserPreferenceImpl(
    private val context: Context
) : UserPreference {
    private val pref = SharedPreferenceUtils.createPreference(context, PREF_NAME)

    override fun isUsingGrid(): Boolean = pref.getBoolean(KEY_IS_USING_GRID, false)

    override fun setUsingGridMode(isUsingGrid: Boolean) {
        pref[KEY_IS_USING_GRID] = isUsingGrid
    }

    companion object {
        const val PREF_NAME = "aresto-pref"
        const val KEY_IS_USING_GRID = "KEY_IS_USING_GRID"
    }
}