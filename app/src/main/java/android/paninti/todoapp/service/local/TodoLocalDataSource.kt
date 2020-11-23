package android.paninti.todoapp.service.local

import android.paninti.todoapp.service.local.dao.TodoDao
import android.paninti.todoapp.service.local.entity.Todo
import javax.inject.Inject

class TodoLocalDataSource @Inject constructor(private val todoDao: TodoDao) {
    suspend fun insertTodo(vararg todo: Todo) {
        todoDao.insertTodo(*todo)
    }

    fun getLocalTodo() = todoDao.getTodo()
}