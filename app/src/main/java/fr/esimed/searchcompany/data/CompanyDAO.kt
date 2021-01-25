package fr.esimed.searchcompany.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update
import fr.esimed.searchcompany.data.model.Company
import fr.esimed.searchcompany.data.model.Search

@Dao
interface CompanyDAO {
    @Insert
    fun insert(company: Company):Int
    @Update
    fun update(company: Company)
    @Delete
    fun delete(company: Company)
}