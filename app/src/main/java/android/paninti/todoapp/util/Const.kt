package android.paninti.todoapp.util

object Const {
    object Database {
        const val databaseName = "todoDatabase"
    }

    object Table {
        const val user = "User"
    }

    object User {
        object Type {
            const val admin = 1
            const val normal = 2

            const val adminName = "Administrator"
            const val normalName = "Normal"
        }
    }
}