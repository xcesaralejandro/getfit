package unko.getfit

import android.content.Context
import android.content.SharedPreferences

class Config() {
    var unidad_de_medida : Int = 0
    var vibracion : Boolean    = true
    var sonido : Boolean       = true
    lateinit var _ctx : Context
    constructor(ctx: Context) : this() {
        this._ctx = ctx
        val sp : SharedPreferences? = ctx.getSharedPreferences("preferences", Context.MODE_PRIVATE)
        this.vibracion              = sp!!.getBoolean("vibracion", true)
        this.sonido                 = sp.getBoolean("sonido", true)
        this.unidad_de_medida       = sp.getInt("unidad_de_medida", 0)
    }

    fun update(){
        val sp : SharedPreferences?       = _ctx.getSharedPreferences("preferences", Context.MODE_PRIVATE)
        val e : SharedPreferences.Editor? = sp!!.edit()
        if (e != null) {
            e.putInt("unidad_de_medida", this.unidad_de_medida)
            e.putBoolean("vibracion", this.vibracion)
            e.putBoolean("sonido", this.sonido)
            e.apply()
            e.commit()
        }
    }
}