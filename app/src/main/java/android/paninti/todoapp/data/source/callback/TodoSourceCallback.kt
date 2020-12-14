package android.paninti.todoapp.data.source.callback

import android.paninti.todoapp.data.local.entity.Todo
import android.paninti.todoapp.data.remote.Result
import android.paninti.todoapp.data.remote.model.User
import androidx.lifecycle.LiveData
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.flow.Flow

interface TodoSourceCallback {
    fun login(email: String, password: String): Flow<Result<AuthResult>>
    fun register(user: User, password: String): Flow<Result<User>>

    suspend fun insertTodo(vararg todo: Todo)
    fun getLocalTodo(): Flow<List<Todo>>

    fun getUsers(): Flow<Result<List<User>>>
    fun addUser(user: User): Flow<Result<Void>>
}