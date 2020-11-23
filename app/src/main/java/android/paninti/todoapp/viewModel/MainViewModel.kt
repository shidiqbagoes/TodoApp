package android.paninti.todoapp.viewModel

import android.app.Application
import android.paninti.todoapp.base.BaseViewModel
import android.paninti.todoapp.service.local.entity.Todo
import android.paninti.todoapp.service.repository.TodoItemRepository
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel @ViewModelInject constructor(
     application: Application,
     private val todoItemRepository: TodoItemRepository
): BaseViewModel(application) {

    init {
        observeNetworkCallback()
    }

    val localTodo = todoItemRepository.getLocalTodo()

    fun insertTodo(vararg todo: Todo) = viewModelScope.launch(Dispatchers.IO) {
        todoItemRepository.insertTodo(*todo)
    }
}