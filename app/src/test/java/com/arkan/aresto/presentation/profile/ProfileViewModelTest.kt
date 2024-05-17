package com.arkan.aresto.presentation.profile

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.arkan.aresto.data.model.User
import com.arkan.aresto.data.repository.UserRepository
import com.arkan.aresto.tools.MainCoroutineRule
import com.arkan.aresto.tools.getOrAwaitValue
import com.arkan.aresto.utils.ResultWrapper
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class ProfileViewModelTest {
    @get:Rule
    val testRule: TestRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val coroutineRule: TestRule = MainCoroutineRule(UnconfinedTestDispatcher())

    @MockK
    lateinit var repo: UserRepository

    private lateinit var viewModel: ProfileViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel = spyk(ProfileViewModel(repo))
    }

    @Test
    fun getCurrentUser() {
        val mockUser = mockk<User>(relaxed = true)
        every {
            repo.getCurrentUser()
        } returns mockUser
        val result = viewModel.getCurrentUser()
        assertEquals(mockUser, result)
        verify { repo.getCurrentUser() }
    }

    @Test
    fun updateProfile() {
        every { repo.updateProfile("Arkan") } returns
            flow {
                emit(
                    ResultWrapper.Success(true),
                )
            }
        val result = viewModel.updateProfile("Arkan").getOrAwaitValue()
        assertEquals(true, result.payload)
        verify { repo.updateProfile("Arkan") }
    }

    @Test
    fun createChangePwdRequest() {
        every {
            repo.requestChangePasswordByEmail()
        } returns true
        val result = viewModel.createChangePwdRequest()
        assertEquals(true, result)
        verify { repo.requestChangePasswordByEmail() }
    }

    @Test
    fun isUserLoggedIn() {
        every {
            repo.isLoggedIn()
        } returns true
        val result = viewModel.isUserLoggedIn()
        assertEquals(true, result)
        verify { repo.isLoggedIn() }
    }

    @Test
    fun doLogout() {
        every {
            repo.doLogout()
        } returns true
        val result = viewModel.doLogout()
        assertEquals(true, result)
        verify { repo.doLogout() }
    }
}
