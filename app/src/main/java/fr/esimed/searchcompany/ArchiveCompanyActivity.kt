package fr.esimed.searchcompany

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import fr.esimed.searchcompany.data.SCDatabase
import fr.esimed.searchcompany.data.model.Company
import fr.esimed.searchcompany.data.model.Search

class ArchiveCompanyActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_archive_company)
        val search=intent.getSerializableExtra("search") as Search
        supportActionBar!!.setTitle(String.format("Resultat(s) de \" %s \"",search.toString()))
        val db=SCDatabase.getDatabase(this)
        val companyDAO=db.companyDAO()
        val listCompany=companyDAO.getCompanyfromsearch(search.id!!)
        val listView=findViewById<ListView>(R.id.LVarchivecompany)
        listView.adapter=ArrayAdapter<Company>(
            this,android.R.layout.simple_dropdown_item_1line,listCompany
        )
        listView.setOnItemClickListener { parent, view, position, id ->
            var elem=listView.getItemAtPosition(position)
            val company=listView.adapter.getItem(position) as Company
            val intent= Intent(this,CompanyActivity::class.java)
            intent.putExtra("company",company)
            this.startActivity(intent)
        }
    }
}