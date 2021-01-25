package fr.esimed.searchcompany.data

import androidx.room.*
import fr.esimed.searchcompany.data.model.Company
import fr.esimed.searchcompany.data.model.Search

@Dao
interface CompanyDAO {

    @Query("Select company.* from company ,keycompanysearch,search where keycompanysearch.idSearch=search.id and keycompanysearch.idCompany=company.id and search.id=:idSearch ")
    fun getCompanyfromsearch(idSearch:Long):List<Company>

    @Query("Select company.* from company where company.archive=0")
    fun getActivCompany():List<Company>

    @Query("Update company set archive=1 where id=:id")
    fun archiveCompany(id:Long)

    @Query("Select id from company where siret=:siret and archive=0")
    fun getCompanybysiret(siret:String):Long

    @Query("Select count(*)from company")
    fun count():Int

    @Insert
    fun insert(company: Company):Long
    @Update
    fun update(company: Company)
    @Delete
    fun delete(company: Company)
}