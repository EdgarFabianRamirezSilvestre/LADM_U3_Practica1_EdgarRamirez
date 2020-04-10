package mx.edu.ittepic.ladm_u3_practica1_edgarramirez

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteException

class Actividad(d: String, fc: String, fe: String) {
    var descripcion = d
    var fechaCaptura = fc
    var fechaEntrega = fe
    var id_actividad = 0
    var error = -1

    val nombreDBHelper = "Practica1"
    var puntero: Context? = null

    fun asignarPuntero(p: Context) {
        puntero = p
    }//asignarPuntero

    fun insertar(): Boolean {
        error = -1
        try {
            var base = BDHelper(puntero!!, nombreDBHelper, null, 1)
            var insertar = base.writableDatabase
            var datos = ContentValues()
            datos.put("DESCRIPCION", descripcion)
            datos.put("FECHACAPTURA", fechaCaptura)
            datos.put("FECHAENTREGA", fechaEntrega)

            var respuesta = insertar.insert("ACTIVIDADES", "ID_ACTIVIDAD", datos)
            if (respuesta.toInt() == -1) {
                error = 2
                return false
            }
        } catch (e: SQLiteException) {
            error = 1
            return false
        }
        return true

    }//insertar

    fun mostrarTodo(): ArrayList<Actividad> {
        var data = ArrayList<Actividad>()
        error = -1
        try {
            var base = BDHelper(puntero!!, nombreDBHelper, null, 1)
            var select = base.readableDatabase
            var columnas = arrayOf("*")
            var cursor = select.query("ACTIVIDADES", columnas, null, null, null, null, null)
            if (cursor.moveToFirst()) {
                do {
                    var actividadTemporal =
                        Actividad(
                            cursor.getString(1),
                            cursor.getString(2),
                            cursor.getString(3)
                        )
                    actividadTemporal.id_actividad = cursor.getInt(0)
                    data.add(actividadTemporal)
                } while (cursor.moveToNext())

            } else {
                error = 3
            }

        } catch (e: SQLiteException) {
            error = 1
        }//catch

        return data
    }//mostrarTodos

    fun mostrarxDescripcion(des:String): ArrayList<Actividad> {
        var data = ArrayList<Actividad>()
        error = -1
        try {
            var base = BDHelper(puntero!!, nombreDBHelper, null, 1)
            var select = base.readableDatabase
            var columnas = arrayOf("*")
            var cursor = select.query("ACTIVIDADES", columnas, "DESCRIPCION = ?",  arrayOf(des), null, null, null)
            if (cursor.moveToFirst()) {
                do {
                    var actividadTemporal =
                        Actividad(
                            cursor.getString(1),
                            cursor.getString(2),
                            cursor.getString(3)
                        )
                    actividadTemporal.id_actividad = cursor.getInt(0)
                    data.add(actividadTemporal)
                } while (cursor.moveToNext())

            } else {
                error = 3
            }

        } catch (e: SQLiteException) {
            error = 1
        }//catch

        return data
    }//mostrarxDescripción

    fun mostrarxFechaCaptura(fc: String): ArrayList<Actividad> {
        var data = ArrayList<Actividad>()
        error = -1
        try {
            var base = BDHelper(puntero!!, nombreDBHelper, null, 1)
            var select = base.readableDatabase
            var columnas = arrayOf("*")
            var cursor = select.query("ACTIVIDADES", columnas, "FECHACAPTURA = ?",  arrayOf(fc), null, null, null)
            if (cursor.moveToFirst()) {
                do {
                    var actividadTemporal =
                        Actividad(
                            cursor.getString(1),
                            cursor.getString(2),
                            cursor.getString(3)
                        )
                    actividadTemporal.id_actividad = cursor.getInt(0)
                    data.add(actividadTemporal)
                } while (cursor.moveToNext())

            } else {
                error = 3
            }

        } catch (e: SQLiteException) {
            error = 1
        }//catch

        return data
    }//mostrarxFechaCaptura

    fun mostrarxFechaEntrega(fe: String): ArrayList<Actividad> {
        var data = ArrayList<Actividad>()
        error = -1
        try {
            var base = BDHelper(puntero!!, nombreDBHelper, null, 1)
            var select = base.readableDatabase
            var columnas = arrayOf("*")
            var cursor = select.query("ACTIVIDADES", columnas, "FECHAENTREGA = ?",  arrayOf(fe), null, null, null)
            if (cursor.moveToFirst()) {
                do {
                    var actividadTemporal =
                        Actividad(
                            cursor.getString(1),
                            cursor.getString(2),
                            cursor.getString(3)
                        )
                    actividadTemporal.id_actividad = cursor.getInt(0)
                    data.add(actividadTemporal)
                } while (cursor.moveToNext())

            } else {
                error = 3
            }

        } catch (e: SQLiteException) {
            error = 1
        }//catch

        return data
    }//mostrarxFechaEntrega

    fun mostrarxIdAct(id:String): ArrayList<Actividad> {
        var data = ArrayList<Actividad>()
        error = -1
        try {
            var base = BDHelper(puntero!!, nombreDBHelper, null, 1)
            var select = base.readableDatabase
            var columnas = arrayOf("*")
            var cursor = select.query("ACTIVIDADES", columnas, "ID_ACTIVIDAD = ?",  arrayOf(id), null, null, null)
            if (cursor.moveToFirst()) {
                do {
                    var actividadTemporal =
                        Actividad(
                            cursor.getString(1),
                            cursor.getString(2),
                            cursor.getString(3)
                        )
                    actividadTemporal.id_actividad = cursor.getInt(0)
                    data.add(actividadTemporal)
                } while (cursor.moveToNext())

            } else {
                error = 3
            }

        } catch (e: SQLiteException) {
            error = 1
        }//catch

        return data
    }//mostrarxIdAct


    fun buscar(id: String): Actividad {
        var actividadEncontrada = Actividad("-1", "-1", "-1")
        error = -1
        try {
            var base = BDHelper(puntero!!, nombreDBHelper, null, 1)
            var select = base.readableDatabase
            var columnas = arrayOf("*")
            var idBuscar = arrayOf(id)
            var cursor = select.query(
                "ACTIVIDADES",
                columnas,
                "ID_ACTIVIDAD = ?",
                idBuscar,
                null,
                null,
                null
            )
            if (cursor.moveToFirst()) {
                actividadEncontrada.id_actividad = id.toInt()
                actividadEncontrada.descripcion = cursor.getString(1)
                actividadEncontrada.fechaCaptura = cursor.getString(2)
                actividadEncontrada.fechaEntrega = cursor.getString(3)

            } else {
                error = 4
            }

        } catch (e: SQLiteException) {
            error = 1
        }
        return actividadEncontrada
    }//buscar



}//class