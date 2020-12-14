package android.paninti.todoapp.data.repositories

import android.paninti.todoapp.data.preferences.AccessManager
import android.paninti.todoapp.data.preferences.AccessOption
import android.paninti.todoapp.data.source.LoginDataSource
import android.paninti.todoapp.data.remote.Result
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.flow.Flow

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */

class LoginRepository(
    private val dataSource: LoginDataSource,
    private val accessManager: AccessManager
) {

    suspend fun logout() {
        dataSource.logout()
        accessManager.setAccess(AccessOption.NO_ACCESS)
    }

    fun login(email: String, password: String): Flow<Result<AuthResult>> {
        return dataSource.login(email, password)
    }

    suspend fun setLoginAccess(accessOption: AccessOption) {
        accessManager.setAccess(accessOption)
    }

    val access = accessManager.access
}