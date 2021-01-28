package fr.esimed.searchcompany

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class LauncherActivity : AppCompatActivity() {
    private val Delay:Long=5000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launcher)

        supportActionBar!!.hide()
        Handler().postDelayed({
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }, Delay)
    }
}