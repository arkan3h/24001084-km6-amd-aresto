package com.arkan.aresto.presentation.register

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.arkan.aresto.data.repository.UserRepository
import com.arkan.aresto.tools.MainCoroutineRule
import com.arkan.aresto.tools.getOrAwaitValue
import com.arkan.aresto.utils.ResultWrapper
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.spyk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class RegisterViewModelTest {
    @get:Rule
    val testRule: TestRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val coroutineRule: TestRule = MainCoroutineRule(UnconfinedTestDispatcher())

    @MockK
    lateinit var repo: UserRepository

    private lateinit var viewModel: RegisterViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel = spyk(RegisterViewModel(repo))
    }

    @Test
    fun doRegister() {
        val email = "email@email.com"
        val password = "password"
        val fullName = "Full Name"

        every {
            repo.doRegister(email, fullName, password)
        } returns
            flow {
                emit(
                    ResultWrapper.Success(true),
                )
            }
        val result = viewModel.doRegister(email, fullName, password).getOrAwaitValue()
        assertEquals(true, result.payload)
        verify { repo.doRegister(email, fullName, password) }
    }
}
