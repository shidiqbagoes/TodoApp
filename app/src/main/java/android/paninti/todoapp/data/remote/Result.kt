package android.paninti.todoapp.data.remote

sealed class Result <T> {
    class Loading<T> : Result<T>()
    class Success<T>(val data: T) : Result<T>()
    class Error<T,X>(val message: String, val data: X? = null): Result<T>()

    companion object {
        fun <T> loading() = Loading<T>()
        fun <T> success(data: T) = Success(data)
        fun <T,X> error(message: String, data: X? = null) = Error<T,X>(message, data)
    }
}