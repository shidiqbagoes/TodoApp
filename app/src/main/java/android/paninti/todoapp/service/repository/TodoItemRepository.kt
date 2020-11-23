package android.paninti.todoapp.service.repository

import android.paninti.todoapp.service.local.TodoLocalDataSource
import android.paninti.todoapp.service.local.entity.Todo
import android.paninti.todoapp.service.source.TodoSourceCallback
import androidx.lifecycle.LiveData
import javax.inject.Inject

class TodoItemRepository @Inject constructor(
        private val todoLocalDataSource: TodoLocalDataSource
) : TodoSourceCallback {

    override suspend fun insertTodo(vararg todo: Todo) {
        todoLocalDataSource.insertTodo(*todo)
    }

    override fun getLocalTodo() = todoLocalDataSource.getLocalTodo()
}