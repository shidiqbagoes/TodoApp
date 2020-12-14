package android.paninti.todoapp.data.source

import android.paninti.todoapp.data.remote.Result
import android.paninti.todoapp.data.remote.model.User
import android.paninti.todoapp.util.Const
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.tasks.await

class TodoRemoteDataSource {
    private val firestore = Firebase.firestore
    private val auth = Firebase.auth

    // Collection / Table
    private val userTable = firestore.collection(Const.Table.user)

    fun login(email: String, password: String) = flow {
        emit(Result.loading())

        val task = auth.signInWithEmailAndPassword(email, password).await()
        emit(Result.success(task))
  }
            .flowOn(Dispatchers.IO)
            .catch { throwable ->
                emit(Result.error(throwable.message.toString(), Any()))
            }


    fun register(user: User, password: String) = flow {
        emit(Result.loading())

        val task = auth.createUserWithEmailAndPassword(user.email, password).await()
        if (task.user?.email == user.email) {
            val currentUser = User().apply {
                uid = task.user?.uid.toString()
                name = user.name
                email = user.email
                phoneNumber = user.phoneNumber
            }
            emit(Result.success(currentUser))
        }
    }.catch {
        emit(Result.error(it.message.toString(), Any()))
    }.flowOn(Dispatchers.IO)

    fun getUsers(isAnonymous: Boolean = false) = flow {
        emit(Result.loading())
        
        val snapShot = if (!isAnonymous) userTable.get().await()
                       else userTable.whereArrayContains(Const.Table.user, auth.currentUser?.uid.toString()).get().await()
        val user = snapShot.toObjects<User>()

        emit(Result.success(user))
    }.catch {
        emit(Result.error(it.message.toString(), Any()))
    }.flowOn(Dispatchers.IO)

    fun addUser(user: User) = flow<Result<Void>> {
        emit(Result.loading())

        val userId = auth.currentUser?.uid.toString()
        val userRef = userTable.document(userId).set(user).await()

        emit(Result.success(userRef))
    }.catch {
        emit(Result.error(it.message.toString(), Any()))
    }.flowOn(Dispatchers.IO)

    fun logout() = auth.signOut()
}