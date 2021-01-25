package fr.esimed.searchcompany.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update
import fr.esimed.searchcompany.data.model.KeyCompanySearch
import fr.esimed.searchcompany.data.model.Search

@Dao
interface KCSDAO {
    @Insert
    fun insert(kcs:KeyCompanySearch):Int
    @Update
    fun update(kcs:KeyCompanySearch)
    @Delete
    fun delete(kcs:KeyCompanySearch)
}