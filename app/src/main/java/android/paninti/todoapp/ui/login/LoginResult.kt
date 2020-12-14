package android.paninti.todoapp.ui.login

/**
 * Authentication result : success (user details) or error message.
 */
data class LoginResult(
        val loading: Unit? = null,
        val success: LoggedInUserView? = null,
        val error: Int? = null
)