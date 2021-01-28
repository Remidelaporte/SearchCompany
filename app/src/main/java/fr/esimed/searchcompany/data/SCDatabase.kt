package fr.esimed.searchcompany.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import fr.esimed.searchcompany.SearchService
import fr.esimed.searchcompany.data.model.CodeNAFAPE
import fr.esimed.searchcompany.data.model.Company
import fr.esimed.searchcompany.data.model.KeyCompanySearch
import fr.esimed.searchcompany.data.model.Search
import java.text.SimpleDateFormat
import java.util.*

@Database(version =1 , entities = [Search::class,KeyCompanySearch::class,Company::class,CodeNAFAPE::class])
abstract class SCDatabase: RoomDatabase() {
    abstract fun searchDAO(): SearchDAO
    abstract fun companyDAO():CompanyDAO
    abstract fun kcsDAO():KCSDAO
    abstract fun cdNDAO():CodeNafDAO

    fun seed(){
        /*val searchDao=searchDAO()
        if (searchDao.count()==0)
        {
            cdNDAO().insert(CodeNAFAPE("00000","n","nul","nul"))
            searchDao.insert(Search(null,"je suis un test","test.com","2021/01/24" ))
            searchDao.insert(Search(null,"je suis un second test","test.com","2021/01/24"))
            searchDao.insert(Search(null,"je suis un troisieme test","test.com","2020/09/24" ))
            val companyDao=companyDAO()
            companyDao.insert(Company(null,"company1","84","chezmoi","rien faire","245210","2021/01/24","0.00000","0.0000"))
            companyDao.insert(Company(null,"company1","84","chezmoi","rien faire","245212","2021/01/24","0.00000","0.0000"))

            companyDao.insert(Company(null,"company1","84","chezmoi","rien faire","245210","2020/09/24","0.00000","0.0000"))
            companyDao.insert(Company(null,"company1","84","chezmoi","rien faire","245212","2020/09/24","0.00000","0.0000"))
            val KCSDao=kcsDAO()
            KCSDao.insert(KeyCompanySearch(1.toLong(),2.toLong()))
            KCSDao.insert(KeyCompanySearch(2.toLong(),1.toLong()))
            KCSDao.insert(KeyCompanySearch(3.toLong(),3.toLong()))
            KCSDao.insert(KeyCompanySearch(3.toLong(),4.toLong()))
        }
        */
    }

    companion object{
        var INSTANCE: SCDatabase?= null

        fun getDatabase(context: Context): SCDatabase {
            if (INSTANCE == null)
            {
                INSTANCE = Room
                    .databaseBuilder(context, SCDatabase::class.java,"searchcompany.db")
                    .allowMainThreadQueries()
                    .build()
                //verifier Instance !=null
            }
            return INSTANCE!!
        }
    }
}