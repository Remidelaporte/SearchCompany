package fr.esimed.searchcompany.data

import androidx.room.*
import fr.esimed.searchcompany.data.model.KeyCompanySearch
import fr.esimed.searchcompany.data.model.Search

@Dao
interface KCSDAO {

    @Query("Select * from keycompanysearch where idSearch=:idsearch")
    fun getKeyfromsearch(idsearch:Long):List<KeyCompanySearch>
    @Insert
    fun insert(kcs:KeyCompanySearch):Long
    @Update
    fun update(kcs:KeyCompanySearch)
    @Delete
    fun delete(kcs:KeyCompanySearch)
}