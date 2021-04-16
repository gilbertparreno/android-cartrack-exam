package com.gilbertparreno.cartrack.authentication.viewModels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.gilbertparreno.cartrack.authentication.managers.AuthenticationManager
import com.gilbertparreno.cartrack.core.networking.taskStatus.TaskStatus
import com.gilbertparreno.cartrack.core.room.daos.UserDao
import com.gilbertparreno.cartrack.utils.TestCoroutineRule
import com.gilbertparreno.cartrack.utils.providers.TestCoroutineContextProvider
import com.jraska.livedata.TestObserver
import com.jraska.livedata.test
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class LoginViewModelTest {

    @get:Rule val instantTestExecutorRule = InstantTaskExecutorRule()
    @get:Rule val testCoroutineRule = TestCoroutineRule()

    @MockK private lateinit var authenticationManager: AuthenticationManager
    @MockK private lateinit var userDao: UserDao

    private lateinit var viewModel: LoginViewModel
    private lateinit var testCoroutineContextProvider: TestCoroutineContextProvider

    private lateinit var testAuthenticateEvent: TestObserver<TaskStatus<Any>>

    private val testEmailAddress = "test@gmail.com"
    private val testPassword = "p@ssword"

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        testCoroutineContextProvider = TestCoroutineContextProvider(testCoroutineRule)
        viewModel = LoginViewModel(
            authenticationManager,
            testCoroutineContextProvider,
            userDao
        ).apply {
            testAuthenticateEvent = authenticateEvent.test()
        }
    }

    @Test
    fun `authenticate() - SUCCESS`() = testCoroutineRule.runBlockingTest {
        coEvery {
            authenticationManager.authenticateUser(
                testEmailAddress,
                testPassword
            )
        } returns Unit
        viewModel.authenticate(testEmailAddress, testPassword)
        coVerify {
            authenticationManager.authenticateUser(testEmailAddress, testPassword)
        }
        testAuthenticateEvent.assertValue { it != null }
    }

    @Test
    fun `getUsers() - FAILURE`() = testCoroutineRule.runBlockingTest {
        val error = Error("This is a test exception")
        coEvery {
            authenticationManager.authenticateUser(
                testEmailAddress,
                testPassword
            )
        } throws error
        viewModel.authenticate(testEmailAddress, testPassword)
        coVerify {
            authenticationManager.authenticateUser(testEmailAddress, testPassword)
        }
        testAuthenticateEvent.assertValue(TaskStatus.error(error))
    }
}