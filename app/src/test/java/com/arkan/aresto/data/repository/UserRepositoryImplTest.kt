package com.arkan.aresto.data.repository

import app.cash.turbine.test
import com.arkan.aresto.data.datasource.auth.AuthDataSource
import com.arkan.aresto.data.datasource.user.UserDataSource
import com.arkan.aresto.data.model.User
import com.arkan.aresto.utils.ResultWrapper
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class UserRepositoryImplTest {
    @MockK
    lateinit var uds: UserDataSource

    @MockK
    lateinit var ads: AuthDataSource

    private lateinit var repo: UserRepository

    private val email = "email@email.com"
    private val password = "password"
    private val fullName = "Full Name"

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        repo = UserRepositoryImpl(uds, ads)
    }

    @Test
    fun isUsingGrid() {
        every {
            repo.isUsingGrid()
        } returns true

        val actualResult = uds.isUsingGrid()
        verify {
            repo.isUsingGrid()
        }
        assertEquals(true, actualResult)
    }

    @Test
    fun setUsingGridMode() {
        every {
            repo.setUsingGridMode(any())
        } returns Unit

        uds.setUsingGridMode(true)
        verify {
            repo.setUsingGridMode(any())
        }
    }

    @Test
    fun doLoginLoading() {
        coEvery {
            ads.doLogin(email, password)
        } returns true

        runTest {
            repo.doLogin(email, password).map {
                delay(100)
                it
            }.test {
                delay(150)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Loading)
                coVerify { ads.doLogin(email, password) }
            }
        }
    }

    @Test
    fun doLoginSuccess() {
        coEvery {
            ads.doLogin(email, password)
        } returns true

        runTest {
            repo.doLogin(email, password).map {
                delay(100)
                it
            }.test {
                delay(250)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Success)
                coVerify { ads.doLogin(email, password) }
            }
        }
    }

    @Test
    fun doLoginError() {
        coEvery {
            ads.doLogin(email, password)
        } throws IllegalStateException("Mock Error")

        runTest {
            repo.doLogin(email, password).map {
                delay(100)
                it
            }.test {
                delay(250)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Error)
                coVerify { ads.doLogin(email, password) }
            }
        }
    }

    @Test
    fun doRegisterLoading() {
        coEvery {
            ads.doRegister(email, fullName, password)
        } returns true

        runTest {
            repo.doRegister(email, fullName, password).map {
                delay(100)
                it
            }.test {
                delay(150)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Loading)
                coVerify { ads.doRegister(email, fullName, password) }
            }
        }
    }

    @Test
    fun doRegisterSuccess() {
        coEvery {
            ads.doRegister(email, fullName, password)
        } returns true

        runTest {
            repo.doRegister(email, fullName, password).map {
                delay(100)
                it
            }.test {
                delay(250)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Success)
                coVerify { ads.doRegister(email, fullName, password) }
            }
        }
    }

    @Test
    fun doRegisterError() {
        coEvery {
            ads.doRegister(email, fullName, password)
        } throws IllegalStateException("Mock Error")

        runTest {
            repo.doRegister(email, fullName, password).map {
                delay(100)
                it
            }.test {
                delay(250)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Error)
                coVerify { ads.doRegister(email, fullName, password) }
            }
        }
    }

    @Test
    fun updateProfileLoading() {
        coEvery {
            ads.updateProfile(fullName)
        } returns true

        runTest {
            repo.updateProfile(fullName).map {
                delay(100)
                it
            }.test {
                delay(150)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Loading)
                coVerify { ads.updateProfile(fullName) }
            }
        }
    }

    @Test
    fun updateProfileSuccess() {
        coEvery {
            ads.updateProfile(fullName)
        } returns true

        runTest {
            repo.updateProfile(fullName).map {
                delay(100)
                it
            }.test {
                delay(250)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Success)
                coVerify { ads.updateProfile(fullName) }
            }
        }
    }

    @Test
    fun updateProfileError() {
        coEvery {
            ads.updateProfile(fullName)
        } throws IllegalStateException("Mock Error")

        runTest {
            repo.updateProfile(fullName).map {
                delay(100)
                it
            }.test {
                delay(250)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Error)
                coVerify { ads.updateProfile(fullName) }
            }
        }
    }

    @Test
    fun updateProfileNoNameLoading() {
        coEvery {
            ads.updateProfile()
        } returns true

        runTest {
            repo.updateProfile().map {
                delay(100)
                it
            }.test {
                delay(150)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Loading)
                coVerify { ads.updateProfile() }
            }
        }
    }

    @Test
    fun updateProfileNoNameSuccess() {
        coEvery {
            ads.updateProfile()
        } returns true

        runTest {
            repo.updateProfile().map {
                delay(100)
                it
            }.test {
                delay(250)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Success)
                coVerify { ads.updateProfile() }
            }
        }
    }

    @Test
    fun updateProfileNoNameError() {
        coEvery {
            ads.updateProfile()
        } throws IllegalStateException("Mock Error")

        runTest {
            repo.updateProfile().map {
                delay(100)
                it
            }.test {
                delay(250)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Error)
                coVerify { ads.updateProfile() }
            }
        }
    }

    @Test
    fun updatePasswordLoading() {
        coEvery {
            ads.updatePassword(password)
        } returns true

        runTest {
            repo.updatePassword(password).map {
                delay(100)
                it
            }.test {
                delay(150)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Loading)
                coVerify { ads.updatePassword(password) }
            }
        }
    }

    @Test
    fun updatePasswordSuccess() {
        coEvery {
            ads.updatePassword(password)
        } returns true

        runTest {
            repo.updatePassword(password).map {
                delay(100)
                it
            }.test {
                delay(250)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Success)
                coVerify { ads.updatePassword(password) }
            }
        }
    }

    @Test
    fun updatePasswordError() {
        coEvery {
            ads.updatePassword(password)
        } throws IllegalStateException("Mock Error")

        runTest {
            repo.updatePassword(password).map {
                delay(100)
                it
            }.test {
                delay(250)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Error)
                coVerify { ads.updatePassword(password) }
            }
        }
    }

    @Test
    fun updateEmailLoading() {
        coEvery {
            ads.updateEmail(email)
        } returns true

        runTest {
            repo.updateEmail(email).map {
                delay(100)
                it
            }.test {
                delay(150)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Loading)
                coVerify { ads.updateEmail(email) }
            }
        }
    }

    @Test
    fun updateEmailSuccess() {
        coEvery {
            ads.updateEmail(email)
        } returns true

        runTest {
            repo.updateEmail(email).map {
                delay(100)
                it
            }.test {
                delay(250)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Success)
                coVerify { ads.updateEmail(email) }
            }
        }
    }

    @Test
    fun updateEmailError() {
        coEvery {
            ads.updateEmail(email)
        } throws IllegalStateException("Mock Error")

        runTest {
            repo.updateEmail(email).map {
                delay(100)
                it
            }.test {
                delay(250)
                val actualData = expectMostRecentItem()
                assertTrue(actualData is ResultWrapper.Error)
                coVerify { ads.updateEmail(email) }
            }
        }
    }

    @Test
    fun requestChangePasswordByEmail() {
        every {
            repo.requestChangePasswordByEmail()
        } returns true

        val actualResult = ads.requestChangePasswordByEmail()
        verify {
            repo.requestChangePasswordByEmail()
        }
        assertEquals(true, actualResult)
    }

    @Test
    fun doLogout() {
        every {
            repo.doLogout()
        } returns true

        val actualResult = ads.doLogout()
        verify {
            repo.doLogout()
        }
        assertEquals(true, actualResult)
    }

    @Test
    fun isLoggedIn() {
        every {
            repo.isLoggedIn()
        } returns true

        val actualResult = ads.isLoggedIn()
        verify {
            repo.isLoggedIn()
        }
        assertEquals(true, actualResult)
    }

    @Test
    fun getCurrentUser() {
        val mockUser = mockk<User>(relaxed = true)
        every {
            repo.getCurrentUser()
        } returns mockUser

        val actualResult = ads.getCurrentUser()
        verify {
            repo.getCurrentUser()
        }
        assertEquals(mockUser, actualResult)
    }
}
