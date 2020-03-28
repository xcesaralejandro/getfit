package unko.getfit

import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import android.support.v4.content.ContextCompat
import android.util.Log
import java.text.DecimalFormat

class Requerimientos (
        // CONSTRUCTOR
        var context : Context                ,
        var peso: Double                     ,
        private var altura: Double           ,
        private val edad: Int                ,
        private val actividad_fisica: String ,
        private val sexo: String             ,
        val unidad_medida :Int        /* 0 - cm y kg / 1 - lb y in */) {

    // PROPS
    private val df         = DecimalFormat("#.0")
    private var factorAgua = 0.9   /*   0,9 a 1,2 */
    private var factorProt = 0.20  /*   15 a 20   */
    private var factorLip  = 0.25  /*   25 a 30   */
    private var factorCh   = 0.55  /*   55 a 60   */

    private var distribucion_desayuno        = 0.2
    private var distribucion_entre_comidas_1 = 0.1
    private var distribucion_almuerzo        = 0.25
    private var distribucion_entre_comidas_2 = 0.1
    private var distribucion_once            = 0.15
    private var distribucion_cena            = 0.2

    init{
        if (unidad_medida == 1){ // 0 cm y kg - 1 in y lb
            this.peso   = this.lbAkg(this.peso)
            this.altura = this.inAcm(altura)
        }
    }

    // Retorna el valor del imc
    fun getImc () :Double{
        val imc :Double = peso / ((altura/100)*(altura/100))
        return df.format(imc).replace(",",".").toDouble()
    }

    // Retorna el peso ideal
    fun getPesoIdeal():Double{
        var peso_ideal= (this.altura/100)*(this.altura/100)
        if(sexo == "hombre")
            peso_ideal *= 21
        else if (sexo == "mujer")
            peso_ideal *= 22
        return this.toWeightMeasureSelectedAndFormat(peso_ideal)
    }

    // Retorna el peso ajustado
    fun getPesoAjustado():Double{
        var peso_ideal = this.getPesoIdeal()
        if (this.unidad_medida == 1) {
            peso_ideal = this.lbAkg(this.getPesoIdeal())
        }
        val peso_ajustado = (this.peso - peso_ideal) * 0.25 + peso_ideal
        return this.toWeightMeasureSelectedAndFormat(peso_ajustado)
    }

    // Retorna el peso minimo aceptable
    fun getMinimoAceptable():Double{
        val minimo_aceptable = 18.5 * ((altura / 100)*(altura / 100))
        return this.toWeightMeasureSelectedAndFormat(minimo_aceptable)
    }

    // Retorna el peso maximo aceptable
    fun getMaximoAceptable():Double{
        val maximo_aceptable = 24.9 * ((altura / 100)*(altura / 100))
        return this.toWeightMeasureSelectedAndFormat(maximo_aceptable)
    }

    // Retorna la cantidad de agua que se debe consumir
    fun getIngestaAgua():Double{
        return df.format((factorAgua * this.getConsumoKcal())/1000).replace(",",".").toDouble()
    }

    // Retorna las kcal que se deben consumir
    fun getConsumoKcal():Int{
        var tbm : Double = 0.0
        var _peso_ideal = this.getPesoIdeal()
        if (this.unidad_medida == 1) _peso_ideal = lbAkg(this.getPesoIdeal())
        when (edad) {
            in 0..2 -> {
                if (sexo == "hombre")
                    tbm = (60.9 * _peso_ideal) - 54
                else if(sexo == "mujer")
                    tbm = (61 * _peso_ideal) - 51
            }
            in 3..9 -> {
                if (sexo == "hombre")
                    tbm = (22.7 * _peso_ideal) + 495
                else if(sexo == "mujer")
                    tbm = (22.5 * _peso_ideal) + 499
            }
            in 10..17 -> {
                if (sexo == "hombre")
                    tbm = (17.5 * _peso_ideal) + 651
                else if(sexo == "mujer")
                    tbm = (12.2 * _peso_ideal) + 746
            }
            in 18..29 -> {
                if (sexo == "hombre")
                    tbm = (15.3 * _peso_ideal) + 679
                else if(sexo == "mujer")
                    tbm = (14.7 * _peso_ideal) + 496
            }
            in 30..60 -> {
                if (sexo == "hombre")
                    tbm = (11.6 * _peso_ideal) + 879
                else if(sexo == "mujer")
                    tbm = (8.7 * _peso_ideal) + 829
            }
            else -> {
                if (sexo == "hombre")
                    tbm = (13.5 * _peso_ideal) + 487
                else if(sexo == "mujer")
                    tbm = (10.5 * _peso_ideal) + 596
            }
        }

        when(actividad_fisica){
            "sedentaria" -> { // Sedentaria
                if (sexo == "hombre")
                    tbm *= 1.2
                else if(sexo == "mujer")
                    tbm *= 1.2

            }
            "liviana" -> {
                if (sexo == "hombre")
                    tbm *= 1.55
                else if(sexo == "mujer")
                    tbm *= 1.56
            }
            "moderada" -> {
                if (sexo == "hombre")
                    tbm *= 1.8
                else if(sexo == "mujer")
                    tbm *= 1.64
            }
            "intensa" -> {
                if (sexo == "hombre")
                    tbm *= 2.1
                else if(sexo == "mujer")
                    tbm *= 1.82
            }
        }
        return tbm.toInt()
    }

    // Retorna el valor de indice peso talla
    fun getIpt():Double{
        var peso_ideal = this.getPesoIdeal()
        if (this.unidad_medida == 1) peso_ideal = this.lbAkg(this.getPesoIdeal())
        return df.format(((peso * 100) / peso_ideal)).replace(",",".").toDouble()
    }

    // Retorna la proteina necesaria en kcal
    fun getProtInKcal():Double{
        val prot = this.getConsumoKcal() * factorProt
        return this.formatValue(prot)
    }

    // Retorna los lipidos necesarios en kcal
    fun getLipInKcal():Double{
        val lip = this.getConsumoKcal() * factorLip
        return this.formatValue(lip)
    }

    // Retorna los carbohidratos necesarios en kcal
    fun getChoInKcal():Double{
        val cho = this.getConsumoKcal() * factorCh
        return this.formatValue(cho)
    }

    // Retorna la proteina necesaria en gramos
    fun getProtInGram():Double{
        val prot = this.getProtInKcal() / 4
        return this.formatValue(prot)
    }

    // Retorna los lipidos necesarios en gramos
    fun getLipInGram():Double{
        val lip = this.getLipInKcal() / 9
        return this.formatValue(lip)
    }

    // Retorna los carbohidratos necesarios en gramos
    fun getChoInGram():Double{
        val cho = this.getChoInKcal() / 4
        return this.formatValue(cho)
    }

    fun sumKcal (): Double {
        val total = this.getLipInKcal() + this.getChoInKcal() + this.getProtInKcal()
        return this.formatValue(total)
    }

    fun sumGram ():Double{
        val total = this.getProtInGram() + this.getChoInGram() + this.getLipInGram()
        return this.formatValue(total)
    }

    fun weightDifference():Double{
        var _peso = this.peso
        if(this.unidad_medida == 1)_peso = this.kgAlb(_peso)
        var diferencia_peso = this.getPesoIdeal() - _peso
        return this.formatValue(diferencia_peso)
    }

    fun getLabelOfIMC():String{
        /*
            Requiere crear las claves de string

            <string name="imc_delgadez_severa">Delgadez severa</string>
            <string name="imc_delgadez_moderada">Delgadez moderada</string>
            <string name="imc_delgadez_aceptable">Delgadez aceptable</string>
            <string name="imc_normal">Peso Normal</string>
            <string name="imc_sobrepeso">Sobrepeso</string>
            <string name="imc_obeso_tipo1">Obeso tipo I</string>
            <string name="imc_obeso_tipo2">Obeso tipo II</string>
            <string name="imc_obeso_tipo3">Obeso tipo III</string>
        */
        when(this.getImc()){
            in -999999999.00..16.00 -> {
                return context.resources.getString(R.string.imc_delgadez_severa)
            }
            in 16.00..16.99 -> {
                return context.resources.getString(R.string.imc_delgadez_moderada)
            }
            in 17.00..18.49 -> {
                return context.resources.getString(R.string.imc_delgadez_aceptable)
            }

            in 18.50..24.99 -> {
                return context.resources.getString(R.string.imc_normal)
            }

            in 25.00..29.99 -> {
                return context.resources.getString(R.string.imc_sobrepeso)
            }

            in 30.00..34.99 -> {
                return context.resources.getString(R.string.imc_obeso_tipo1)
            }

            in 35.00..39.99 -> {
                return context.resources.getString(R.string.imc_obeso_tipo2)
            }

            in 40.00..999999999.00 -> {
                return context.resources.getString(R.string.imc_obeso_tipo3)
            }
        }

        return "- -"
    }

    fun getKcalDesayuno (): Double { return this.formatValue(this.getConsumoKcal() * distribucion_desayuno) }
    fun getKcalColacion1(): Double { return this.formatValue(this.getConsumoKcal() * distribucion_entre_comidas_1)}
    fun getKcalAlmuerzo() : Double { return this.formatValue(this.getConsumoKcal() * distribucion_almuerzo) }
    fun getKcalColacion2(): Double { return this.formatValue(this.getConsumoKcal() * distribucion_entre_comidas_2) }
    fun getKcalOnce()     : Double { return this.formatValue(this.getConsumoKcal() * distribucion_once) }
    fun getKcalCena()     : Double { return this.formatValue(this.getConsumoKcal() * distribucion_cena) }

    // Funciones de conversión
    fun lbAkg(lb : Double): Double { return lb / 2.205}
    fun kgAlb(kg : Double): Double { return kg * 2.205 }
    fun cmAin(cm : Double): Double { return cm / 2.54 }
    fun inAcm(inch : Double): Double { return inch * 2.54 }
    fun formatValue (value :Any):Double{ return df.format(value).replace(",",".").toDouble()}
    fun toWeightMeasureSelectedAndFormat (value :Double): Double  {
        return if (this.unidad_medida == 1)
            this.formatValue(this.kgAlb(value))
        else
            this.formatValue(value)
    }

}