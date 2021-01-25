package fr.esimed.searchcompany

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import fr.esimed.searchcompany.data.model.Company

class CompanyActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_company)
        val company=intent.getSerializableExtra("company")as Company
        findViewById<TextView>(R.id.TVCompanyLibelle).setText(company.libelle.toString())

        findViewById<TextView>(R.id.TVCompanydepart).setText(company.depart.toString())

        findViewById<TextView>(R.id.TVCompanyadress).setText(company.adress.toString())

        findViewById<TextView>(R.id.TVCompanyactivity).setText(company.activity.toString())

        findViewById<TextView>(R.id.TVCompanysiret).setText(company.siret.toString())
    }
}