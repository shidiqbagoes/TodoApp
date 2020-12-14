package android.paninti.todoapp.data.repositories

import android.paninti.todoapp.data.source.TodoLocalDataSource
import android.paninti.todoapp.data.local.entity.Todo
import android.paninti.todoapp.data.source.TodoRemoteDataSource
import android.paninti.todoapp.data.remote.model.User
import android.paninti.todoapp.data.source.callback.TodoSourceCallback
import javax.inject.Inject

class TodoItemRepository @Inject constructor(
    private val todoRemoteDataSource: TodoRemoteDataSource,
    private val todoLocalDataSource: TodoLocalDataSource
) : TodoSourceCallback {

    override fun login(email: String, password: String) =
        todoRemoteDataSource.login(email, password)

    override fun register(user: User, password: String) =
        todoRemoteDataSource.register(user, password)

    override fun getUsers() = todoRemoteDataSource.getUsers()

    override fun addUser(user: User) =
        todoRemoteDataSource.addUser(user)


    override suspend fun insertTodo(vararg todo: Todo) {
        todoLocalDataSource.insertTodo(*todo)
    }

    override fun getLocalTodo() = todoLocalDataSource.getLocalTodo()
}