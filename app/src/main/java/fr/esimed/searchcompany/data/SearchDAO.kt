package fr.esimed.searchcompany.data

import androidx.room.*
import fr.esimed.searchcompany.data.model.Company
import fr.esimed.searchcompany.data.model.Search

@Dao
interface SearchDAO {

    @Query("Select search.id from search where search.URL=:url and search.archive=0")
    fun getIdifalready(url:String):Long

    @Query("Select search.* from search where search.archive=0")
    fun getActivSearch():List<Search>

    @Query("Update search set archive=1 where search.id=:id")
    fun archiveSearch(id:Long)

    @Query("Select search.* from search ")
    fun getArchiveSearch():List<Search>

    @Query("Select count(*)from search")
    fun count():Int
    @Insert
    fun insert(search:Search):Long

    @Update
    fun update(search: Search)

    @Delete
    fun delete(search: Search)
}