package com.gilbertparreno.cartrack.main.viewModels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.gilbertparreno.cartrack.core.networking.repositories.UserRepository
import com.gilbertparreno.cartrack.core.networking.taskStatus.TaskStatus
import com.gilbertparreno.cartrack.main.entities.User
import com.gilbertparreno.cartrack.main.factories.UserFactory
import com.gilbertparreno.cartrack.utils.TestCoroutineRule
import com.gilbertparreno.cartrack.utils.providers.TestCoroutineContextProvider
import com.jraska.livedata.TestObserver
import com.jraska.livedata.test
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.coVerifyOrder
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class MainViewModelTest {

    @get:Rule val instantTestExecutorRule = InstantTaskExecutorRule()
    @get:Rule val testCoroutineRule = TestCoroutineRule()

    @MockK private lateinit var userRepository: UserRepository

    private lateinit var viewModel: MainViewModel
    private lateinit var testCoroutineContextProvider: TestCoroutineContextProvider

    private lateinit var testUsersStatusData: TestObserver<TaskStatus<List<User>>>

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        testCoroutineContextProvider = TestCoroutineContextProvider(testCoroutineRule)
        viewModel = MainViewModel(
            userRepository,
            testCoroutineContextProvider
        ).apply {
            testUsersStatusData = usersStatus.test()
        }
    }

    @Test
    fun `getUsers() - SUCCESS`() = testCoroutineRule.runBlockingTest {
        coEvery { userRepository.getUsers() } returns listOf()
        viewModel.getUsers()
        coVerifyOrder {
            userRepository.getUsers()
            UserFactory.createUserFromUserApiList(listOf())
        }
        testUsersStatusData.assertValueHistory(
            TaskStatus.loading(true),
            TaskStatus.success(listOf())
        )
    }

    @Test
    fun `getUsers() - FAILURE`() = testCoroutineRule.runBlockingTest {
        val error = Error("This is a test exception")
        coEvery { userRepository.getUsers() } throws error
        viewModel.getUsers()
        coVerify {
            userRepository.getUsers()
        }
        testUsersStatusData.assertValueHistory(
            TaskStatus.loading(true),
            TaskStatus.error(error)
        )
    }
}