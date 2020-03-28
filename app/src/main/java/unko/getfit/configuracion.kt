package unko.getfit

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import kotlinx.android.synthetic.main.activity_configuracion.*
import kotlinx.android.synthetic.main.activity_resultado_calculadora_requerimientos.*
import java.util.ArrayList

class configuracion : AppCompatActivity() {
    val ctx = this
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_configuracion)
        val myToolbar = toolbar_configutarion
        setSupportActionBar(myToolbar)

        banner_config.visibility = View.GONE
        val adRequest = AdRequest.Builder().build()
        banner_config.loadAd(adRequest)

        banner_config.adListener = object: AdListener() {
            override fun onAdLoaded() {
                banner_config.visibility = View.VISIBLE
                super.onAdLoaded()
            }
        }

        val medidas = ArrayList<String>()
        medidas.add(resources.getString(R.string.sp_medida_cm_y_kg))
        medidas.add(resources.getString(R.string.sp_medida_in_y_lb))
        sp_medidas.attachDataSource<String>(medidas)

        val cfg : Config = Config(this)
        sp_medidas.selectedIndex = cfg.unidad_de_medida
        sw_sonido.isChecked = cfg.sonido
        sw_vibracion.isChecked = cfg.vibracion

        btn_actualizar_preferencias.setOnClickListener {
            cfg.sonido = sw_sonido.isChecked
            cfg.vibracion = sw_vibracion.isChecked
            cfg.unidad_de_medida = sp_medidas.selectedIndex
            cfg.update()
            Toast.makeText(this, resources.getString(R.string.actualizar_preferencias), Toast.LENGTH_SHORT).show()
        }
    }
    override fun onBackPressed() {
        val i : Intent = Intent(this, calculadora_requerimientos::class.java)
        startActivity(i)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_configuracion, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId
        when(id){
            R.id.go_home -> {
                val i : Intent = Intent(this, calculadora_requerimientos::class.java)
                startActivity(i)
            }
            else -> super.onOptionsItemSelected(item)

        }
        return super.onOptionsItemSelected(item)
    }
}
