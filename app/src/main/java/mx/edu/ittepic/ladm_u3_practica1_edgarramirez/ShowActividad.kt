package mx.edu.ittepic.ladm_u3_practica1_edgarramirez

import android.database.sqlite.SQLiteException
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import android.widget.ArrayAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_buscar.*
import kotlinx.android.synthetic.main.activity_show.*
import java.util.ArrayList
import mx.edu.ittepic.ladm_u3_practica1_edgarramirez.Utils.Utils
import java.lang.Exception


class ShowActividad : AppCompatActivity() {
    var id=0
    var listaID = ArrayList<String>()
    internal lateinit var dbHelper:BDHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show)

        var extra = intent.extras

        id=extra!!.getInt("id_actividad")
        editId.setText(id.toString())
        editDescripcion.setText(extra.getString("descripcion"))
        editFechaCaptura.setText(extra.getString("fechaCaptura"))
        editFechaEntrega.setText(extra.getString("fechaEntrega"))
        cargarImg(editId.text.toString())
        desactivarEdits()

        dbHelper= BDHelper(this,"Practica1",null,1)
        btnMostrarEvidencia.setOnClickListener {
            cargarImg(editId.text.toString())
        }//btnMostrarEvidencia

        btnEliminarActividad.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("¿SEGURO QUE QUIERES ELIMINAR LA ACTIVIDAD?")
                .setMessage("SE ELIMINARA DICHA ACTIVIDAD CON SUS EVIDENCIAS CORRESPONDIENTES")
                .setPositiveButton("ELIMINAR"){d,i->
                    eliminarActividad(editId.text.toString())
                    finish()
                }
                .show()
        }//btnEliminarActividad

    }//onCreate

    fun cargarImg(id: String) {
        listaID = ArrayList<String>()
        try {
            var baseDatos = BDHelper(this, "Practica1", null, 1)
            var select = baseDatos.readableDatabase
            var SQL = "SELECT * FROM EVIDENCIAS WHERE Id_actividad = ?"

            var parametros = arrayOf(id)
            var cursor = select.rawQuery(SQL, parametros)
            if(cursor.count<=0){
                var idEv= ArrayList<String>()
                var idAct= ArrayList<String>()
                var arreglo = ArrayList<Bitmap>()
                val array = arrayOfNulls<Bitmap>(arreglo.size)
                var arrayid= arrayOfNulls<String>(idEv.size)
                var arrayidAct= arrayOfNulls<String>(idAct.size)
                val adapter =  AdaptadorLista(this, idEv.toArray(arrayid),
                    idAct.toArray(arrayidAct),
                    arreglo.toArray(array))
                listView.adapter = adapter
            }//if
            if (cursor.count > 0) {
                var bit: Bitmap? = null
                var arreglo = ArrayList<Bitmap>()
                this.listaID = ArrayList<String>()
                var idEv=ArrayList<String>()
                var idAct=ArrayList<String>()
                cursor.moveToFirst()
                var cantidad = cursor.count - 1
                (0..cantidad).forEach {
                    arreglo.add(Utils.getImage(buscarImg(cursor.getString(0).toInt())!!))
                    listaID.add(cursor.getString(0  ))

                    idEv.add(cursor.getString(0))
                    idAct.add(cursor.getString(1))
                    cursor.moveToNext()

                }//forEach

                val array = arrayOfNulls<Bitmap>(arreglo.size)
                var arrayid= arrayOfNulls<String>(idEv.size)
                var arrayidAct= arrayOfNulls<String>(idAct.size)
                val miAdaptador = AdaptadorLista(this, idEv.toArray(arrayid),idAct.toArray(arrayidAct),arreglo.toArray(array))

                listView.adapter = miAdaptador
                listView.setOnItemClickListener { parent, view, position, id ->
                    AlertDialog.Builder(this)
                        .setTitle("ELIMINAR")
                        .setMessage("¿SEGURO QUE QUIERES ELIMINAR ESTA EVIDENCIA?" +"\nID Evidencia = ${listaID[position]}")
                        .setPositiveButton("ELIMINAR") { d, i ->
                            eliminarEvidenciaxID(listaID[position])
                            cargarImg(editId.text.toString())
                        }

                        .setNegativeButton("CANCELAR") { d, i -> }
                        .show()
                }//setOnItemClickListener

            } else {
                mensaje("NO SE ENCONTRARON EVIDENCIAS")
            }
            select.close()
            baseDatos.close()
        } catch (error: SQLiteException) {
            mensaje(error.message.toString())
        }//catch
    }//cargarImg

    fun eliminarEvidenciaxID(id: String) {
        try {
            var BD = BDHelper(this, "Practica1", null, 1)
            var borrar = BD.writableDatabase
            var SQL = "DELETE FROM EVIDENCIAS WHERE IDEVIDENCIA=?"
            var seleccion = arrayOf(id)

            borrar.execSQL(SQL, seleccion)
            mensaje("SE HA ELIMINADO LA EVIDENCIA CORRECTAMENTE")
            borrar.close()
            BD.close()
        } catch (error: SQLiteException) {
            mensaje(error.message.toString())
        }//catch
    }//eliminarEvidenciaxID

    fun eliminarActividad(id: String) {
        try {
            var BD = BDHelper(this, "Practica1", null, 1)
            var eliminar = BD.writableDatabase
            var SQLEvidencia = "DELETE FROM EVIDENCIAS WHERE ID_ACTIVIDAD=?"

            var parametros = arrayOf(id)
            eliminar.execSQL(SQLEvidencia, parametros)
            var SQLActividad = "DELETE FROM ACTIVIDADES WHERE ID_ACTIVIDAD=?"
            eliminar.execSQL(SQLActividad, parametros)
            mensaje("SE HA ELIMINADO LA ACTIVIDAD CORRECTAMENTE")
            eliminar.close()
            BD.close()
        } catch (error: SQLiteException) {
            mensaje(error.message.toString())
        }//catch
    }//eliminarActividad

    fun mensaje(s: String) {
        Toast.makeText(this, s, Toast.LENGTH_LONG).show()
    }//mensaje

    fun buscarImg(id: Int): ByteArray? {
        var base = BDHelper(this, "Practica1", null, 1)
        var buscar = base.readableDatabase
        var columnas = arrayOf("FOTO") // * = todas las columnas
        var cursor = buscar.query(
            "EVIDENCIAS",
            columnas,
            "IDEVIDENCIA = ?",
            arrayOf(id.toString()),
            null,
            null,
            null
        )
        var result: ByteArray? = null
        if (cursor.moveToFirst()) {
            do {
                result = cursor.getBlob(cursor.getColumnIndex("FOTO"))
            } while (cursor.moveToNext())
        }//if
        return result
    }//buscarImg

    fun desactivarEdits(){
        editId.isFocusable=false
        editDescripcion.isFocusable=false
        editFechaEntrega.isFocusable=false
        editFechaCaptura.isFocusable=false
    }//desactivarEdits

}//class
