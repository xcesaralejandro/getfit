package unko.getfit

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.google.android.gms.ads.MobileAds

class MainActivity : AppCompatActivity() {
    val ctx : Context = this
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        MobileAds.initialize(this, getString(R.string.admob_app_id))
        val sp : SharedPreferences? = ctx.getSharedPreferences("preferences", Context.MODE_PRIVATE)
        Handler().postDelayed({

            if (sp!!.getBoolean("firstAccess", true)){
                val e : SharedPreferences.Editor? = sp.edit()
                e!!.putBoolean("firstAccess", false)
                e.apply()
                e.commit()
                val mainIntent = Intent(this, App_intro::class.java)
                startActivity(mainIntent)
            }else{
                val mainIntent = Intent(this, calculadora_requerimientos::class.java)
                startActivity(mainIntent)
            }
            //finish()
        }, 500)
    }
}
