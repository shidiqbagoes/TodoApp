package android.paninti.todoapp.service.local.dao

import android.paninti.todoapp.service.local.entity.Todo
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface TodoDao {
    @Insert
    suspend fun insertTodo(vararg todo: Todo)

    @Query("SELECT * FROM todo")
    fun getTodo(): LiveData<List<Todo>>
}