package mx.edu.ittepic.ladm_u3_practica1_edgarramirez

import android.content.ContentValues
import android.database.sqlite.SQLiteException
import android.content.Context
import android.database.sqlite.SQLiteQueryBuilder
import android.media.Image
import androidx.core.database.getIntOrNull

class Evidencia(idAct:String, fot: ByteArray?) {

    var foto = fot
    var idActividad = idAct
    var idEvidencia = 0
    var error = -1

    val nombreBD = "Practica1"
    var puntero: Context? = null

    fun asignarPuntero(p: Context) {
        puntero = p
    }

    fun insertarImagen(): Boolean {
        try {
            var base = BDHelper(puntero!!, nombreBD, null, 1)
            var insertar = base.writableDatabase
            var datos = ContentValues()
            datos.put("ID_ACTIVIDAD", idActividad)
            datos.put("FOTO", foto)
            var respuesta = insertar.insert("EVIDENCIAS", "IDEVIDENCIA", datos)
            if (respuesta.toInt() == -1) {
                error = 2
                return false
            }
        } catch (e: SQLiteException) {
            error = 1
            return false
        }//catch
        return true
    }//insertarImagen

    fun buscarImagen(id: Int): ByteArray? {
        var base = BDHelper(puntero!!, nombreBD, null, 1)
        var buscar = base.readableDatabase
        var columnas = arrayOf("FOTO") // * = todas las columnas
        var cursor = buscar.query(
            "EVIDENCIAS", columnas, "IDEVIDENCIA = ?",
            arrayOf(id.toString()), null, null, null
        )
        var result: ByteArray? = null
        if (cursor.moveToFirst()) {
            do {
                result = cursor.getBlob(cursor.getColumnIndex("FOTO"))
            } while (cursor.moveToNext())
        }//if
        return result
    }//buscarImagen

}//class