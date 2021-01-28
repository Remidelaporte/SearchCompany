package fr.esimed.searchcompany

import android.content.Context
import android.util.JsonReader
import android.util.JsonToken
import android.widget.Spinner
import fr.esimed.searchcompany.data.SCDatabase
import fr.esimed.searchcompany.data.SearchDAO
import fr.esimed.searchcompany.data.model.CodeNAFAPE
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
    private var queryURL="$apiUrl/%s"
    private val extendURL="?page=1&per_page=10"
    private val codeURL="?code_postal=%s&"
    private val departURL="?departement=%s&"
    private val activityURL="?activite_principale=%s&"
    private val context=context

    fun getCompagny(query:String,codeint:String,spinner: CodeNAFAPE?):Long{
        val db=SCDatabase.getDatabase(context)
        val searchDAO=db.searchDAO()
        val companyDAO=db.companyDAO()
        val kcsdao=db.kcsDAO()
        val sdf = SimpleDateFormat("yyyy/MM/dd")
        val c = Calendar.getInstance()
        val date = sdf.format(c.time).toString()
        var urltemp=String.format("$queryURL",query)
        var url:URL
        when(codeint.length){
            2->{
                urltemp="$urltemp$departURL"
                urltemp=String.format(urltemp,codeint)
            }
            5->{
                urltemp="$urltemp$codeURL"
                urltemp=String.format(urltemp,codeint)
            }
        }
        if (spinner!=null)
        {
            urltemp="$urltemp$activityURL"
            urltemp=String.format(urltemp,spinner.CodeNAFAPE)
        }
        url=URL("$urltemp$extendURL")
        var ifalready=searchDAO.getIdifalready(url.toString())
        if(ifalready!=0.toLong())
        {
            return ifalready
        }
        var conn:HttpsURLConnection?=null
        var nom=""
        var addr=""
        var depart=""
        var activ=""
        var siret=""
        var longitude=""
        var lattitude=""
        try {
            conn=url.openConnection()as HttpsURLConnection
            conn.connect()
            val code=conn.responseCode
            if (code!=HttpsURLConnection.HTTP_OK)
            {
                return 0.toLong()
            }
            val inputStream=conn.inputStream?:return 0.toLong()
            val reader=JsonReader(inputStream.bufferedReader())
            val insertquery="$query-$codeint"
            var idsearch=searchDAO.insert(Search(null,insertquery,url.toString(),date))
            reader.beginObject()
            while (reader.hasNext()){
                var firstsuivant=reader.nextName()
                if (firstsuivant=="etablissement")
                {
                    reader.beginArray()
                    while (reader.hasNext())
                    {
                        reader.beginObject()
                        while (reader.hasNext())
                        {
                            when(reader.nextName()){
                                "nom_raison_sociale"-> {
                                    if (reader.peek() == JsonToken.NULL) {
                                        reader.nextNull()
                                        nom = "Nom non indiqué"
                                    } else {
                                        nom = reader.nextString()
                                    }
                                }
                                "geo_adresse"->{
                                    if (reader.peek() == JsonToken.NULL)  {
                                        reader.nextNull()
                                        addr = "Adresse non indiquée"
                                    }else{
                                        addr = reader.nextString()
                                    }
                                }
                              "departement"->
                              {
                                  if (reader.peek() == JsonToken.NULL)  {
                                      reader.nextNull()
                                      depart = "Département non indiqué"
                                  }else{
                                      depart= reader.nextString()
                                  }
                              }
                                "libelle_activite_principale"->{
                                    if (reader.peek() == JsonToken.NULL)  {
                                        reader.nextNull()
                                        activ = "Activité non indiqué"
                                    }else{
                                        activ = reader.nextString()
                                    }
                                }
                                "siret"->{
                                    if (reader.peek() == JsonToken.NULL)  {
                                        reader.nextNull()
                                        siret = "Siret non indiqué"
                                    }else{
                                        siret = reader.nextString()
                                    }
                                }
                                "longitude"->{
                                    if (reader.peek() == JsonToken.NULL)  {
                                        reader.nextNull()
                                        longitude = "0.00000"
                                    }else{
                                        longitude = reader.nextString()
                                    }
                                }
                                "latitude"->{
                                    if (reader.peek() == JsonToken.NULL)  {
                                        reader.nextNull()
                                        lattitude = "0.00000"
                                    }else{
                                        lattitude = reader.nextString()
                                    }
                                }
                                else->reader.skipValue()
                            }
                        }
                        var result=companyDAO.getCompanybysiret(siret)
                        var idcompany=0.toLong()
                        if (result==0.toLong())
                        {
                            idcompany=companyDAO.insert(Company(null,nom,depart,addr,activ,siret,date,longitude,lattitude))
                        }
                        else{
                            idcompany=result
                        }
                        kcsdao.insert(KeyCompanySearch(idsearch,idcompany))
                        reader.endObject()
                    }
                    return idsearch
                }
                else
                {
                    reader.skipValue()
                }

            }
            return 0.toLong()
        }catch (e:IOException){
            return 0.toLong()
        }finally {
            conn?.disconnect()
        }
    }
}