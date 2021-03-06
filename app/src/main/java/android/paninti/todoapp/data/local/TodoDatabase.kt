package android.paninti.todoapp.data.local

import android.paninti.todoapp.data.local.dao.TodoDao
import android.paninti.todoapp.data.local.entity.Todo
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
        entities = [Todo::class],
        version = 1,
        exportSchema = false
) abstract class TodoDatabase : RoomDatabase() {

    abstract fun todoDao(): TodoDao
}