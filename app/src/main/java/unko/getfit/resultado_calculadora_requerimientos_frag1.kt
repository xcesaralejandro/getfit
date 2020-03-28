package unko.getfit

import android.os.Bundle
import android.os.Parcelable
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.resultado_calculadora_requerimientos_frag1_layout.view.*
import android.graphics.Shader
import android.graphics.drawable.BitmapDrawable
import android.graphics.BitmapFactory
import android.graphics.Bitmap



class resultado_calculadora_requerimientos_frag1 : Fragment() {
    var cfg :Config? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v : View = inflater.inflate(R.layout.resultado_calculadora_requerimientos_frag1_layout, container, false)
         var requerimientos: Requerimientos? = null
         if (arguments != null) {
             val bundle = arguments!!
             requerimientos = Requerimientos(
                     v.context,
                     bundle.getDouble("peso"),
                     bundle.getDouble("altura"),
                     bundle.getInt("edad"),
                     bundle.getString("actividad_fisica"),
                     bundle.getString("sexo"),
                     bundle.getInt("unidad_medida")
             )
         }
         this.cfg = Config(v.context)

         v.txt_imc_number.text    = requerimientos!!.getImc().toString()
         v.txt_peso_minimo.text   = requerimientos.getMinimoAceptable().toString()
         v.txt_peso_ideal.text    = requerimientos.getPesoIdeal().toString()
         v.txt_peso_maximo.text   = requerimientos.getMaximoAceptable().toString()
         v.txt_peso_objetivo.text = requerimientos.getPesoAjustado().toString()
         v.txt_imc_legend.text    = requerimientos.getLabelOfIMC()


         this.setUnidadMedida(v)
         var txt_peso_diference : String = ""
         Log.d("WASKJAS", requerimientos.weightDifference().toString())
         if (requerimientos.weightDifference() < 0){
             var peso = requerimientos.weightDifference()
             if (peso < 0){
                 peso *= -1
             }
             txt_peso_diference = "${resources.getString(R.string.txt_estas)} $peso${this.unidad_peso()} ${resources.getString(R.string.txt_sobre_tu_peso_ideal)}"
         }

        if (requerimientos.weightDifference() > 0){
            var peso = requerimientos.weightDifference()
            if (peso < 0){
                peso *= -1
            }
            txt_peso_diference = "${resources.getString(R.string.txt_estas)} $peso${this.unidad_peso()} ${resources.getString(R.string.txt_bajo_tu_peso_ideal)}"
        }

        if (requerimientos.weightDifference() == 0.0){
            txt_peso_diference = resources.getString(R.string.txt_en_tu_peso_ideal)
        }

         val diferencia_peso_string = txt_peso_diference
         v.txt_diferencia_peso.text = diferencia_peso_string

        return v
    }

    fun unidad_peso():String{
       if (this.cfg!!.unidad_de_medida == 0)
            return "kg"
        else (this.cfg!!.unidad_de_medida == 1)
            return "lb"
    }

    fun setUnidadMedida(v : View){
        v.txt_medida_peso_minimo_aceptable.text = this.unidad_peso()
        v.txt_medida_peso_ideal.text            = this.unidad_peso()
        v.txt_peso_maximo_aceptable.text        = this.unidad_peso()
        v.txt_medida_peso_objetivo.text         = this.unidad_peso()
    }
}
