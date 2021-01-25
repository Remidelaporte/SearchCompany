package fr.esimed.searchcompany

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import fr.esimed.searchcompany.data.SCDatabase
import fr.esimed.searchcompany.data.model.Company
import fr.esimed.searchcompany.data.model.Search

class ArchiveActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_archive)
        val listView=findViewById<ListView>(R.id.LVarchive)
        val db= SCDatabase.getDatabase(this)
        val searchDAO=db.searchDAO()
        val liste=searchDAO.getArchiveSearch()
        listView.adapter=ArrayAdapter<Search>(
            this,android.R.layout.simple_dropdown_item_1line,liste
        )
        listView.setOnItemClickListener { parent, view, position, id ->
            var elem=listView.getItemAtPosition(position)
            val search=listView.adapter.getItem(position) as Search
            val intent= Intent(this,ArchiveCompanyActivity::class.java)
            intent.putExtra("search",search)
            this.startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        val listView=findViewById<ListView>(R.id.LVarchive)
        val db= SCDatabase.getDatabase(this)
        val searchDAO=db.searchDAO()
        val liste=searchDAO.getArchiveSearch()
        listView.adapter=ArrayAdapter<Search>(
            this,android.R.layout.simple_dropdown_item_1line,liste
        )
        listView.setOnItemClickListener { parent, view, position, id ->
            var elem=listView.getItemAtPosition(position)
            val search=listView.adapter.getItem(position) as Search
            val intent= Intent(this,ArchiveCompanyActivity::class.java)
            intent.putExtra("search",search)
            this.startActivity(intent)
        }
    }
}