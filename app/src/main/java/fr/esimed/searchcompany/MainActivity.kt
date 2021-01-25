package fr.esimed.searchcompany

import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import fr.esimed.searchcompany.data.model.Company

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
    }
    inner class QueryTask():AsyncTask<Void,Void,Boolean>(){
        val searchservice=SearchService()
        var liste= emptyList<Company>()
        var listView=findViewById<ListView>(R.id.LVcompanyList)
        override fun doInBackground(vararg params: Void?): Boolean {
            liste=searchservice.getCompagny(findViewById<EditText>(R.id.ETsearchName).text.toString())
            return true
        }

        override fun onPostExecute(result: Boolean?) {
            if(result!=null)
            {
                listView.adapter=ArrayAdapter<Company>(
                    this@MainActivity,android.R.layout.simple_dropdown_item_1line,liste
                )
            }
        }
    }
}