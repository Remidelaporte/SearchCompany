package fr.esimed.searchcompany

import android.content.Context
import android.util.JsonReader
import fr.esimed.searchcompany.data.SCDatabase
import fr.esimed.searchcompany.data.SearchDAO
import fr.esimed.searchcompany.data.model.Company
import fr.esimed.searchcompany.data.model.KeyCompanySearch
import fr.esimed.searchcompany.data.model.Search
import java.io.IOException
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*
import javax.net.ssl.HttpsURLConnection

class SearchService(context: Context) {
    private val apiUrl="https://entreprise.data.gouv.fr/api/sirene/v1/full_text"
    private val queryURL="$apiUrl/%s?page=1&per_page=10"
    private val context=context

    fun getCompagny(query:String):List<Company>{
        val db=SCDatabase.getDatabase(context)
        val searchDAO=db.searchDAO()
        val companyDAO=db.companyDAO()
        val kcsdao=db.kcsDAO()
        val sdf = SimpleDateFormat("yyyy/MM/dd")
        val c = Calendar.getInstance()
        val date = sdf.format(c.time).toString()
        val url= URL(String.format(queryURL,query))
        var conn:HttpsURLConnection?=null
        val result= mutableListOf<Company>()
        var nom=""
        var addr=""
        var depart=0
        var activ=""
        var siret=""
        try {
            conn=url.openConnection()as HttpsURLConnection
            conn.connect()
            val code=conn.responseCode
            if (code!=HttpsURLConnection.HTTP_OK)
            {
                return emptyList()
            }
            val inputStream=conn.inputStream?:return emptyList()
            val reader=JsonReader(inputStream.bufferedReader())
            var idsearch=searchDAO.insert(Search(null,query,url.toString(),date))
            reader.beginObject()
            while (reader.hasNext()){
                var firstsuivant=reader.nextName()
                println(firstsuivant)
                if (firstsuivant=="etablissement")
                {
                    reader.beginArray()
                    while (reader.hasNext())
                    {
                        reader.beginObject()
                        while (reader.hasNext())
                        {
                            when(reader.nextName()){
                                "nom_raison_sociale"->nom=reader.nextString().toString()
                                "geo_adresse"->addr=reader.nextString().toString()
                                "departement"->depart=reader.nextInt().toInt()
                                "libelle_activite_principale"->activ=reader.nextString().toString()
                                "siret"->siret=reader.nextString().toString()
                                else->reader.skipValue()
                            }
                        }
                        println(Company(null,nom,depart,addr,activ,siret))
                        var idcompany=companyDAO.insert(Company(null,nom,depart,addr,activ,siret))
                        kcsdao.insert(KeyCompanySearch(idsearch,idcompany))
                        reader.endObject()
                    }
                    return result
                }
                else
                {
                    reader.skipValue()
                }

            }
            return emptyList()
        }catch (e:IOException){
            return emptyList()
        }finally {
            conn?.disconnect()
        }
    }
}