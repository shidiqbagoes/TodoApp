package android.paninti.todoapp.viewmodel

import android.app.Application
import android.paninti.todoapp.R
import android.paninti.todoapp.base.BaseViewModel
import android.paninti.todoapp.data.preferences.AccessOption
import android.paninti.todoapp.data.remote.Result
import android.util.Patterns
import android.paninti.todoapp.data.repositories.LoginRepository
import android.paninti.todoapp.ui.login.LoggedInUserView
import android.paninti.todoapp.ui.login.LoginFormState
import android.paninti.todoapp.ui.login.LoginResult
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class LoginViewModel @ViewModelInject constructor(
    application: Application,
    private val loginRepository: LoginRepository
    ) : BaseViewModel(application) {

    init {
        observeNetworkCallback()
    }

    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> get() = _loginResult

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    fun login(email: String, password: String) = loginRepository
        .login(email, password)
        .onEach { result ->
            when(result) {
                is Result.Loading -> {
                    _loginResult.value = LoginResult(loading = null)
                }
                is Result.Success -> {
                    loginRepository.setLoginAccess(AccessOption.HAS_ACCESS)
                    _loginResult.value = LoginResult(
                            success = LoggedInUserView(result.data.user?.email .toString())
                    )
                }
                is Result.Error<*, *> -> {
                    _loginResult.value = LoginResult(error = R.string.login_failed)
                }
            }
        }.launchIn(viewModelScope)

    fun loginDataChanged(username: String, password: String) {
        if (!isUserNameValid(username)) {
            _loginForm.value = LoginFormState(usernameError = R.string.invalid_username)
        } else if (!isPasswordValid(password)) {
            _loginForm.value = LoginFormState(passwordError = R.string.invalid_password)
        } else {
            _loginForm.value = LoginFormState(isDataValid = true)
        }
    }

    // A placeholder username validation check
    private fun isUserNameValid(username: String): Boolean {
        return if (username.contains("@")) {
            Patterns.EMAIL_ADDRESS.matcher(username).matches()
        } else {
            username.isNotBlank()
        }
    }

    // A placeholder password validation check
    private fun isPasswordValid(password: String): Boolean {
        return password.length > 5
    }

    val loginAccess = loginRepository.access
            .asLiveData(viewModelScope.coroutineContext)

    fun logout() = viewModelScope.launch {
        loginRepository.logout()
    }
}