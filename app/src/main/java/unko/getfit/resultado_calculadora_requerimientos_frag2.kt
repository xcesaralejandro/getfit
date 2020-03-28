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

import java.util.ArrayList

class resultado_calculadora_requerimientos_frag2 : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.resultado_calculadora_requerimientos_frag2_layout, container, false)
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

        // Set results in layout
        v.txt_water.text              = requerimientos!!.getIngestaAgua().toString()
        v.txt_kcal.text               = requerimientos.getConsumoKcal().toString()
        v.txt_proteina_g.text         = requerimientos.getProtInGram().toString()
        v.txt_proteina_kcal.text      = requerimientos.getProtInKcal().toString()
        v.txt_carbohidratos_g.text    = requerimientos.getChoInGram().toString()
        v.txt_carbohidratos_kcal.text = requerimientos.getChoInKcal().toString()
        v.txt_lipido_g.text           = requerimientos.getLipInGram().toString()
        v.txt_lipido_kcal.text        = requerimientos.getLipInKcal().toString()

        v.txt_total_g.text       = requerimientos.sumGram().toString()
        v.txt_total_kcal.text    = requerimientos.sumKcal().toString()


        val entries = ArrayList<PieEntry>()
        entries.add(PieEntry(requerimientos.getProtInKcal().toFloat(), "PROT"))
        entries.add(PieEntry(requerimientos.getLipInKcal().toFloat(), "LIP"))
        entries.add(PieEntry(requerimientos.getChoInKcal().toFloat(), "CHO"))

        val set = PieDataSet(entries, "Election Results")
        val data = PieData(set)
        val pieChart = v.chart_macronutriente
        pieChart.data = data
        set.setColors(Color.rgb(222, 216, 87),
                Color.rgb(255, 181, 122),
                Color.rgb(226, 126, 158))
        set.valueTextSize = 18.toFloat()
        set.valueTextColor = Color.rgb(255, 255, 255)
        pieChart.invalidate() // refresh
        return v
    }
}
