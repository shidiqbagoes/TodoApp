package android.paninti.todoapp.data.source

import android.paninti.todoapp.data.local.dao.TodoDao
import android.paninti.todoapp.data.local.entity.Todo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class TodoLocalDataSource @Inject constructor(private val todoDao: TodoDao) {
    suspend fun insertTodo(vararg todo: Todo) {
        todoDao.insertTodo(*todo)
    }

    fun getLocalTodo() = todoDao
        .getTodo()
        .flowOn(Dispatchers.IO)
}