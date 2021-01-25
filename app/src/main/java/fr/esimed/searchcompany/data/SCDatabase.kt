package fr.esimed.searchcompany.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import fr.esimed.searchcompany.data.model.Company
import fr.esimed.searchcompany.data.model.KeyCompanySearch
import fr.esimed.searchcompany.data.model.Search
import java.text.SimpleDateFormat
import java.util.*

@Database(version =1 , entities = [Search::class,KeyCompanySearch::class,Company::class])
abstract class SCDatabase: RoomDatabase() {
    abstract fun searchDao(): SearchDAO
    abstract fun companyDao():CompanyDAO
    abstract fun kcsDAO():KCSDAO


    companion object{
        var INSTANCE: SCDatabase?= null

        fun getDatabase(context: Context): SCDatabase {
            if (INSTANCE == null)
            {
                INSTANCE = Room
                    .databaseBuilder(context, SCDatabase::class.java,"speeddating.db")
                    .allowMainThreadQueries()
                    .build()
                //verifier Instance !=null
            }
            return INSTANCE!!
        }
    }
}