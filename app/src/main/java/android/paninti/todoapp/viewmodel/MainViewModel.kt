package android.paninti.todoapp.viewmodel

import android.app.Application
import android.paninti.todoapp.base.BaseViewModel
import android.paninti.todoapp.data.remote.Result
import android.paninti.todoapp.data.remote.model.User
import android.paninti.todoapp.data.repositories.TodoItemRepository
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers

class MainViewModel @ViewModelInject constructor(
     application: Application,
     private val todoItemRepository: TodoItemRepository
): BaseViewModel(application) {

    private val viewModelContext = Dispatchers.IO + viewModelScope.coroutineContext

    init {
        observeNetworkCallback()
    }

    fun login(email: String, password: String) = todoItemRepository
            .login(email, password)
            .asLiveData(viewModelScope.coroutineContext)

    fun register(user: User, password: String) = todoItemRepository
            .register(user, password)
            .asLiveData(viewModelContext)

    private val _users = todoItemRepository
            .getUsers()
            .asLiveData(viewModelContext)

    val users: LiveData<Result<List<User>>> get() = _users

    fun addUser(user: User) = todoItemRepository
            .addUser(user)
            .asLiveData(viewModelContext)

}