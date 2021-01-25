package fr.esimed.searchcompany.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import fr.esimed.searchcompany.SearchService
import fr.esimed.searchcompany.data.model.Company
import fr.esimed.searchcompany.data.model.KeyCompanySearch
import fr.esimed.searchcompany.data.model.Search
import java.util.*

@Database(version =1 , entities = [Search::class,KeyCompanySearch::class,Company::class])
abstract class SCDatabase: RoomDatabase() {
    abstract fun searchDAO(): SearchDAO
    abstract fun companyDAO():CompanyDAO
    abstract fun kcsDAO():KCSDAO


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