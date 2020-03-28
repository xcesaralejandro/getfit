package unko.getfit

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Vibrator
import android.support.v4.content.ContextCompat
import android.support.v4.content.res.ResourcesCompat
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd
import com.google.android.gms.ads.MobileAds
import kotlinx.android.synthetic.main.activity_calculadora_requerimientos.*


class calculadora_requerimientos : AppCompatActivity() {

    val ctx = this
    private var actividad_fisica : String? = null
    private var sexo     : String = ""
    private lateinit var mInterstitialAd: InterstitialAd
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculadora_requerimientos)
        val vibratorService = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        val cfg = Config(baseContext)
        //MobileAds.initialize(this,getString(R.string.admob_app_id))
        mInterstitialAd = InterstitialAd(this)
        mInterstitialAd.adUnitId = getString(R.string.admob_intersitial)
        mInterstitialAd.loadAd(AdRequest.Builder().build())

        btn_config.setOnClickListener{
            val intent = Intent(ctx, configuracion::class.java)
            startActivity(intent)
        }
        // Btn calcular
        btn_calcular.setOnClickListener {
            val peso     = et_peso.text.toString().toDoubleOrNull()
            val altura   = et_altura.text.toString().toDoubleOrNull()
            val edad: Double?     = et_edad.text.toString().toDoubleOrNull()
            var hasError = false
            if (peso != null && altura != null && edad != null && validMedidas(peso, altura, edad)){
                error_medidas.visibility = View.GONE
            }else{
                error_medidas.text = "${resources.getString(R.string.label_mis_datos_actuales)} ${resources.getString(R.string.error_required)}"
                error_medidas.visibility = View.VISIBLE
                hasError = true
            }

            if (this.actividad_fisica != null){
                mi_actividad_fisica.visibility = View.GONE
            }else{
                mi_actividad_fisica.text = "${resources.getString(R.string.mi_actividad_fisica_semanal)} ${resources.getString(R.string.error_required)}"
                mi_actividad_fisica.visibility = View.VISIBLE
                hasError = true
            }

            if (sexo.isNotEmpty()){
                genero.visibility = View.GONE
            }else{
                genero.text = "${resources.getString(R.string.label_sexo)} ${resources.getString(R.string.error_required)}"
                genero.visibility = View.VISIBLE
                hasError = true
            }

            if (hasError){
                if(cfg.vibracion){
                    vibratorService.vibrate(200)
                    Toast.makeText(ctx, resources.getString(R.string.toat_error_validation), Toast.LENGTH_LONG).show()
                }
            }else{
                val intent = Intent(ctx, resultado_calculadora_requerimientos::class.java)
                val bundle = Bundle()
                bundle.putDouble("peso",et_peso.text.toString().toDouble())
                bundle.putDouble("altura",et_altura.text.toString().toDouble())
                bundle.putInt("edad", et_edad.text.toString().toInt())
                bundle.putString("actividad_fisica",this.actividad_fisica!!)
                bundle.putString("sexo",sexo)
                bundle.putInt("unidad_medida", cfg.unidad_de_medida)
                intent.putExtra("bundle", bundle)
                if (mInterstitialAd.isLoaded) {
                    mInterstitialAd.show()
                } else {
                    startActivity(intent)
                }

                mInterstitialAd.adListener = object: AdListener() {
                    override fun onAdLoaded() {
                    }

                    override fun onAdFailedToLoad(errorCode: Int) {
                        startActivity(intent)
                    }

                    override fun onAdClosed() {
                        startActivity(intent)
                    }
                }
            }
        }

        /* Seleccion sobre el tipo de regulacion (Relajada - normal - rapido)*/

        sedentaria.setOnClickListener {
            wantWithMyBody("sedentaria")
        }

        liviana.setOnClickListener {
            wantWithMyBody("liviana")
        }

        moderada.setOnClickListener {
            wantWithMyBody("moderada")
        }
        intensa.setOnClickListener {
            wantWithMyBody("intensa")
        }

        /* Seleccion del genero*/
        male.setOnClickListener {
            changeGender("hombre")
        }

        female.setOnClickListener {
            changeGender("mujer")
        }
    }

    /* Funciones de validación */
    private fun validMedidas (peso : Double, altura : Double, edad: Double): Boolean {
        return peso > 0 && altura > 0 && edad > 0 && edad < 120
    }

    private fun changeGender( gender : String){
        if (gender == "hombre" && sexo != "hombre"){
             sexo = "hombre"
             female.background = ResourcesCompat.getDrawable(resources, R.drawable.bg_gender_noselected, null)
             male.background   = ResourcesCompat.getDrawable(resources, R.drawable.bg_gender_selected, null)
             txt_men.bringToFront()
        }else if (gender == "mujer" && sexo != "mujer"){
             sexo = "mujer"
             male.background   = ResourcesCompat.getDrawable(resources, R.drawable.bg_gender_noselected, null)
             female.background = ResourcesCompat.getDrawable(resources, R.drawable.bg_gender_selected, null)
             txt_girl.bringToFront()
        }
    }

    private fun wantWithMyBody(want : String){
        when (want){
            "sedentaria" -> {
                if(actividad_fisica != "sedentaria"){
                    changeBackgroundIfWillChange("sedentaria")
                }
            }
            "liviana" -> {
                if (actividad_fisica != "liviana"){
                    changeBackgroundIfWillChange("liviana")
                }
            }
            "moderada" ->{
                if (actividad_fisica != "moderada"){
                    changeBackgroundIfWillChange("moderada")
                }
            }

            "intensa" ->{
                if (actividad_fisica != "intensa"){
                    changeBackgroundIfWillChange("intensa")
                }
            }

        }
    }

    private fun changeBackgroundIfWillChange(active : String){
        when(actividad_fisica){
            "sedentaria"->{
                if ( sedentaria.background.constantState == ContextCompat.getDrawable(ctx,R.drawable.bg_rectangle_selected)!!.constantState)
                    sedentaria.background = ResourcesCompat.getDrawable(resources, R.drawable.bg_rectangle_no_selected, null)
            }
            "liviana" ->{
                if ( liviana.background.constantState == ContextCompat.getDrawable(ctx,R.drawable.bg_rectangle_selected)!!.constantState)
                    liviana.background = ResourcesCompat.getDrawable(resources, R.drawable.bg_rectangle_no_selected, null)

            }
            "moderada" ->{
                if ( moderada.background.constantState == ContextCompat.getDrawable(ctx,R.drawable.bg_rectangle_selected)!!.constantState)
                    moderada.background = ResourcesCompat.getDrawable(resources, R.drawable.bg_rectangle_no_selected, null)
            }
            "intensa" ->{
                if ( intensa.background.constantState == ContextCompat.getDrawable(ctx,R.drawable.bg_rectangle_selected)!!.constantState)
                    intensa.background = ResourcesCompat.getDrawable(resources, R.drawable.bg_rectangle_no_selected, null)
            }
        }

        when(active){
           "sedentaria"->{
               if ( sedentaria.background.constantState == ContextCompat.getDrawable(ctx,R.drawable.bg_rectangle_no_selected)!!.constantState)
                   sedentaria.background = ResourcesCompat.getDrawable(resources, R.drawable.bg_rectangle_selected, null)
            }
           "liviana" ->{
               if ( liviana.background.constantState == ContextCompat.getDrawable(ctx,R.drawable.bg_rectangle_no_selected)!!.constantState)
                   liviana.background = ResourcesCompat.getDrawable(resources, R.drawable.bg_rectangle_selected, null)
            }
           "moderada" ->{
               if ( moderada.background.constantState == ContextCompat.getDrawable(ctx,R.drawable.bg_rectangle_no_selected)!!.constantState)
                   moderada.background = ResourcesCompat.getDrawable(resources, R.drawable.bg_rectangle_selected, null)
           }
            "intensa" ->{
                if ( intensa.background.constantState == ContextCompat.getDrawable(ctx,R.drawable.bg_rectangle_no_selected)!!.constantState)
                    intensa.background = ResourcesCompat.getDrawable(resources, R.drawable.bg_rectangle_selected, null)
            }
        }
        actividad_fisica = active
    }

    // Con esta linea deshabilitamos el boton volver atras para que no se vaya al splash
    override fun onBackPressed() {
    }
}
