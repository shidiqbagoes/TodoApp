package android.paninti.todoapp.service.source

import android.paninti.todoapp.service.local.entity.Todo
import androidx.lifecycle.LiveData

interface TodoSourceCallback {
    suspend fun insertTodo(vararg todo: Todo)
    fun getLocalTodo(): LiveData<List<Todo>>
}