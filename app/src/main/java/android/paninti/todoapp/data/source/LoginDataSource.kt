package android.paninti.todoapp.data.source

import android.paninti.todoapp.data.remote.Result
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.tasks.await

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class LoginDataSource {

    private val auth = Firebase.auth

    fun login(email: String, password: String): Flow<Result<AuthResult>> = flow {
        emit(Result.loading())

        val task = auth.signInWithEmailAndPassword(email, password).await()
        emit(Result.success(task))
    }.flowOn(Dispatchers.IO)
        .catch { throwable ->
            emit(Result.error(throwable.message.toString(), Any()))
        }

    fun logout() {
        auth.signOut()
    }
}