package unko.getfit

import android.graphics.Color
import android.os.Bundle
import android.os.Parcelable
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.github.mikephil.charting.charts.Chart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import kotlinx.android.synthetic.main.resultado_calculadora_requerimientos_frag2_layout.view.*
import kotlinx.android.synthetic.main.resultado_calculadora_requerimientos_frag3_layout.view.*

import java.util.ArrayList

class resultado_calculadora_requerimientos_frag3 : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.resultado_calculadora_requerimientos_frag3_layout, container, false)
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

        v.txt_kcal_desayuno.text        = requerimientos!!.getKcalDesayuno().toString()
        v.txt_kcal_entre_comidas_1.text = requerimientos.getKcalColacion1().toString()
        v.txt_kcal_almuerzo.text        = requerimientos.getKcalAlmuerzo().toString()
        v.txt_kcal_entre_comidas_2.text = requerimientos.getKcalColacion2().toString()
        v.txt_kcal_once.text            = requerimientos.getKcalOnce().toString()
        v.txt_kcal_cena.text            = requerimientos.getKcalCena().toString()

        return v
    }
}
