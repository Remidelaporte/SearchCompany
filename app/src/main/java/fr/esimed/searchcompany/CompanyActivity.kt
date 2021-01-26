package fr.esimed.searchcompany

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
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
        var mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(OnMapReadyCallback {
            var googleMap = it

            val loc1 = LatLng(company.latitude!!.toDouble(), company.longitude!!.toDouble())
            googleMap.addMarker(MarkerOptions().position(loc1).title(company.libelle).visible(true))
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(loc1,14f))
        })
    }
}