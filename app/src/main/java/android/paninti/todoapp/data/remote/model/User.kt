package android.paninti.todoapp.data.remote.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
   var uid: String = "",
   var name: String = "",
   var username: String = "",
   var email: String = "",
   var phoneNumber: String = "",
   //var role: Role = Role(Type.normal, Type.normalName)
): Parcelable {
    @Parcelize
    data class Role(
        val id: Int = 0,
        val name: String = ""
    ): Parcelable
}