package fr.esimed.searchcompany

import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import fr.esimed.searchcompany.data.SCDatabase
import fr.esimed.searchcompany.data.model.Company
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class MainActivity : AppCompatActivity() {
    private val SAVEDIDSEARCH="idsearch"
    private val savedidsearch=null
    private var searchid:Long = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val db= SCDatabase.getDatabase(this@MainActivity)
        db.seed()
        setContentView(R.layout.activity_main)
        //si jamais y a un idsearch dans les saveinstance on fait l'appel par search pour construire l'adapter
        if(savedInstanceState!=null && savedInstanceState.containsKey(SAVEDIDSEARCH))
        {
            val sid=savedInstanceState.getLong(SAVEDIDSEARCH)
            val liste=db.companyDAO().getCompanyfromsearch(sid)
            findViewById<ListView>(R.id.LVcompanyList).adapter=ArrayAdapter<Company>(
                    this@MainActivity,android.R.layout.simple_dropdown_item_1line,liste
            )
        }
        findViewById<ImageButton>(R.id.BTsearch).setOnClickListener {
            QueryTask().execute()
        }
        val listView=findViewById<ListView>(R.id.LVcompanyList)
        listView.setOnItemClickListener { parent, view, position, id ->
            var elem=listView.getItemAtPosition(position)
            val company=listView.adapter.getItem(position) as Company
            val intent=Intent(this,CompanyActivity::class.java)
            intent.putExtra("company",company)
            this.startActivity(intent)
        }
        archive()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putLong(SAVEDIDSEARCH, searchid)
    }

    fun archive(){
        val db= SCDatabase.getDatabase(this)
        val searchDAO=db.searchDAO()
        val listSearch=searchDAO.getActivSearch()
        val companyDAO=db.companyDAO()
        val kcsDAO=db.kcsDAO()

        //date du jour
        val sdf=SimpleDateFormat("yyyy/MM/dd")
        val c=Calendar.getInstance()
        val datetoday=sdf.format(c.time)
        //ligne pour changer le string en date pour le comparer
        val datetodaycompar = sdf.parse(datetoday)

        //date de la veille
        c.add(Calendar.DAY_OF_WEEK, -1);
        val dateyesterday = c.getTime()
        val dateyest = sdf.format(dateyesterday)
        val dateyestcompar = sdf.parse(dateyest)


        //date il y a trois mois
        c.add(Calendar.MONTH, -3);
        val datepasse = c.getTime()
        val datepast = sdf.format(datepasse)
        val datepastcompar = sdf.parse(datepast)

        for(i in listSearch)
        {
            val datesearch=sdf.parse(i.date)
            if (datesearch<=datepastcompar)
            {
                println("je delete")
                val listcompagny=companyDAO.getCompanyfromsearch(i.id!!)
                for(j in listcompagny)
                {
                    println(j.toString())
                   companyDAO.delete(j)
                }
                val listkey=kcsDAO.getKeyfromsearch(i.id!!)
                for(k in listkey)
                {
                    println(k.toString())
                    kcsDAO.delete(k)
                }
                println(i.toString())
                searchDAO.delete(i)
            }
            else
            {
                if (datesearch<=dateyestcompar)
                {
                    val listCompany=companyDAO.getCompanyfromsearch(i.id!!)
                    for (j in listCompany)
                    {
                        companyDAO.archiveCompany(j.id!!)
                    }
                    searchDAO.archiveSearch(i.id!!)
                }
            }
        }
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main,menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        println(item.itemId)
        when(item.itemId){
            R.id.menu_archive ->this.startActivity(Intent(this,ArchiveActivity::class.java))
        }
        return true
    }
    inner class QueryTask():AsyncTask<Void,Void,Boolean>(){
        val searchservice=SearchService(this@MainActivity)
        var liste= emptyList<Company>()
        var idSearch=0.toLong()
        var listView=findViewById<ListView>(R.id.LVcompanyList)
        val db= SCDatabase.getDatabase(this@MainActivity)
        val searchDAO=db.searchDAO()
        val companyDAO=db.companyDAO()
        val kcsdao=db.kcsDAO()
        override fun doInBackground(vararg params: Void?): Boolean {
            idSearch=searchservice.getCompagny(findViewById<EditText>(R.id.ETsearchName).text.toString(),findViewById<EditText>(R.id.ETcode).text.toString())
            return true
        }

        override fun onPostExecute(result: Boolean?) {
            if(result!=null)
            {
                println(idSearch)
                if(idSearch!=0.toLong())
                {
                    searchid=idSearch
                    liste=companyDAO.getCompanyfromsearch(idSearch)
                    println(liste)
                    listView.adapter=ArrayAdapter<Company>(
                        this@MainActivity,android.R.layout.simple_dropdown_item_1line,liste
                    )
                }
            }
        }

        override fun onPreExecute() {
            listView.adapter = null
        }
    }
}