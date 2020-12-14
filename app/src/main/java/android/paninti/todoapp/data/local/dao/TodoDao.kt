package android.paninti.todoapp.data.local.dao

import android.paninti.todoapp.data.local.entity.Todo
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao {
    @Insert
    suspend fun insertTodo(vararg todo: Todo)

    @Query("SELECT * FROM todo")
    fun getTodo(): Flow<List<Todo>>
}