package com.arkan.aresto.data.source.pref

import android.content.SharedPreferences
import com.arkan.aresto.utils.SharedPreferenceUtils.set

interface UserPreference {
    fun isUsingGrid(): Boolean

    fun setUsingGridMode(isUsingGrid: Boolean)
}

class UserPreferenceImpl(
    private val pref: SharedPreferences,
) : UserPreference {
    override fun isUsingGrid(): Boolean = pref.getBoolean(KEY_IS_USING_GRID, false)

    override fun setUsingGridMode(isUsingGrid: Boolean) {
        pref[KEY_IS_USING_GRID] = isUsingGrid
    }

    companion object {
        const val PREF_NAME = "aresto-pref"
        const val KEY_IS_USING_GRID = "KEY_IS_USING_GRID"
    }
}
