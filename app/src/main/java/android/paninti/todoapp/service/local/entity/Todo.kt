package android.paninti.todoapp.service.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todo")
data class Todo(
        @PrimaryKey
        var id: Long = 0,
        var name: String = ""
)