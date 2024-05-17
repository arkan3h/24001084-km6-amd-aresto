package com.arkan.aresto.data.datasource.auth

import com.arkan.aresto.data.model.toUser
import com.arkan.aresto.data.source.firebase.FirebaseService
import com.google.firebase.auth.FirebaseUser
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class FirebaseAuthDataSourceTest {
    @MockK
    lateinit var service: FirebaseService

    private lateinit var dataSource: AuthDataSource

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        dataSource = FirebaseAuthDataSource(service)
    }

    @Test
    fun doLogin() {
        runTest {
            coEvery {
                service.doLogin(any(), any())
            } returns true

            val actualResult = dataSource.doLogin("arekaneh1@gmail.com", "password123")
            coVerify {
                service.doLogin(any(), any())
            }
            assertEquals(true, actualResult)
        }
    }

    @Test
    fun doRegister() {
        runTest {
            coEvery {
                service.doRegister(any(), any(), any())
            } returns true

            val actualResult = dataSource.doRegister("arekaneh1@gmail.com", "Arkan", "password123")
            coVerify {
                service.doRegister(any(), any(), any())
            }
            assertEquals(true, actualResult)
        }
    }

    @Test
    fun updateProfileWithData() {
        runTest {
            coEvery {
                service.updateProfile(any())
            } returns true

            val actualResult = dataSource.updateProfile("Arkan")
            coVerify {
                service.updateProfile(any())
            }
            assertEquals(true, actualResult)
        }
    }

    @Test
    fun updateProfileWithoutData() {
        runTest {
            coEvery {
                service.updateProfile()
            } returns true

            val actualResult = dataSource.updateProfile()
            coVerify {
                service.updateProfile()
            }
            assertEquals(true, actualResult)
        }
    }

    @Test
    fun updatePassword() {
        runTest {
            coEvery {
                service.updatePassword(any())
            } returns true

            val actualResult = dataSource.updatePassword("password123")
            coVerify {
                service.updatePassword(any())
            }
            assertEquals(true, actualResult)
        }
    }

    @Test
    fun updateEmail() {
        runTest {
            coEvery {
                service.updateEmail(any())
            } returns true

            val actualResult = dataSource.updateEmail("arekaneh1@gmail.com")
            coVerify {
                service.updateEmail(any())
            }
            assertEquals(true, actualResult)
        }
    }

    @Test
    fun requestChangePasswordByEmail() {
        every {
            service.requestChangePasswordByEmail()
        } returns true

        val actualResult = dataSource.requestChangePasswordByEmail()
        verify {
            service.requestChangePasswordByEmail()
        }
        assertEquals(true, actualResult)
    }

    @Test
    fun doLogout() {
        every {
            service.doLogout()
        } returns true

        val actualResult = dataSource.doLogout()
        verify {
            service.doLogout()
        }
        assertEquals(true, actualResult)
    }

    @Test
    fun isLoggedIn() {
        every {
            service.isLoggedIn()
        } returns true

        val actualResult = dataSource.isLoggedIn()
        verify {
            service.isLoggedIn()
        }
        assertEquals(true, actualResult)
    }

    @Test
    fun getCurrentUser() {
        val mockFirebaseUser = mockk<FirebaseUser>(relaxed = true)
        every {
            service.getCurrentUser()
        } returns mockFirebaseUser

        val result = mockFirebaseUser.toUser()
        val actualResult = dataSource.getCurrentUser()
        verify {
            service.getCurrentUser()
        }
        assertEquals(result, actualResult)
    }
}
