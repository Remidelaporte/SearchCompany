package fr.esimed.searchcompany.data

import androidx.room.*
import fr.esimed.searchcompany.data.model.Search

@Dao
interface SearchDAO {
    @Insert
    fun insert(search:Search):Long
    @Update
    fun update(search: Search)
    @Delete
    fun delete(search: Search)
}