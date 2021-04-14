package com.gilbertparreno.cartrack.main.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gilbertparreno.cartrack.core.extensions.SingleLiveEvent
import com.gilbertparreno.cartrack.core.extensions.launch
import com.gilbertparreno.cartrack.core.networking.repositories.UserRepository
import com.gilbertparreno.cartrack.core.networking.taskStatus.TaskStatus
import com.gilbertparreno.cartrack.main.entities.User
import com.gilbertparreno.cartrack.main.factories.UserFactory
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    val usersStatus = SingleLiveEvent<TaskStatus<List<User>>>()

    fun getUsers() {
        viewModelScope.launch(work = {
            val usersApi = userRepository.getUsers()
            UserFactory.createUserFromUserApiList(usersApi)
        }, onSuccess = {
            usersStatus.value = TaskStatus.success(it)
        }, onFailure = {
            usersStatus.value = TaskStatus.error(it)
        })
    }
}