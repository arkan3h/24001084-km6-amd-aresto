package com.arkan.aresto.data.datasource.user

import com.arkan.aresto.data.source.pref.UserPreference
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class UserPreferenceDataSourceTest {
    @MockK
    lateinit var pref: UserPreference

    private lateinit var dataSource: UserDataSource

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        dataSource = UserPreferenceDataSource(pref)
    }

    @Test
    fun isUsingGrid() {
        every {
            pref.isUsingGrid()
        } returns true

        val actualResult = dataSource.isUsingGrid()
        verify {
            pref.isUsingGrid()
        }
        assertEquals(true, actualResult)
    }

    @Test
    fun setUsingGridMode() {
        every {
            pref.setUsingGridMode(any())
        } returns Unit

        dataSource.setUsingGridMode(true)
        verify {
            pref.setUsingGridMode(any())
        }
    }
}
