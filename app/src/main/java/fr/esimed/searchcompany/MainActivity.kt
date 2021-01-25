package fr.esimed.searchcompany

import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import fr.esimed.searchcompany.data.SCDatabase
import fr.esimed.searchcompany.data.model.Company
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val db= SCDatabase.getDatabase(this@MainActivity)
        db.seed()
        setContentView(R.layout.activity_main)
        findViewById<Button>(R.id.BTsearch).setOnClickListener {
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
    fun archive(){
        val sdf=SimpleDateFormat("yyyy/MM/dd")
        val c=Calendar.getInstance()
        val date=sdf.format(c.time)
        val db= SCDatabase.getDatabase(this)
        val searchDAO=db.searchDAO()
        val listSearch=searchDAO.getActivSearch()
        val companyDAO=db.companyDAO()
        for(i in listSearch)
        {
            println("cherche i")
            println(i.date)
            if (i.date!=date)
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
            idSearch=searchservice.getCompagny(findViewById<EditText>(R.id.ETsearchName).text.toString())
            return true
        }

        override fun onPostExecute(result: Boolean?) {
            if(result!=null)
            {
                println(idSearch)
                if(idSearch!=0.toLong())
                {
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