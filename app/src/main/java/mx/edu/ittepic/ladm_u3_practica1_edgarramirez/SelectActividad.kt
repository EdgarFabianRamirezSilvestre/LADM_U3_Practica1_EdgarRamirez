package mx.edu.ittepic.ladm_u3_practica1_edgarramirez

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.EditText
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_select.*
import mx.edu.ittepic.ladm_u3_practica1_edgarramirez.Utils.Utils

class SelectActividad : AppCompatActivity() {
    val SELECT_PHOTO = 2222
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select)

        btnElegir.setOnClickListener {
            val photoPicker = Intent(Intent.ACTION_PICK)
            photoPicker.type = "image/*"
            startActivityForResult(photoPicker, SELECT_PHOTO)
        }//btnElegir

        btnGuardar.setOnClickListener {
            val bitmap = (imgSelect.drawable as BitmapDrawable).bitmap
            val alertDialog = AlertDialog.Builder(this)
            alertDialog.setTitle("INGRESA EL ID DE LA ACTIVIDAD")
            val editText = EditText(this)
            alertDialog.setView((editText))
            alertDialog.setPositiveButton("ACEPTAR") { dialog, which ->
                var evidencia = Evidencia(editText.text.toString(), Utils.getBytes(bitmap))
                evidencia.asignarPuntero(this)
                var resultado = evidencia.insertarImagen()
                if (resultado == true) {
                    mensaje("SE GUARDO LA EVIDENCIA CORRECTAMENTE")
                } else {
                    when (evidencia.error) {
                        1 -> {
                            dialogo("!ERROR DE TABLA!, FALLO EN CREACION O CONEXION CON LA BASE DE DATOS")
                        }//1
                        2 -> {
                            dialogo("!ERROR DE INSERCION EN LA TABLA!")
                        }//2
                    }//when
                }//else
            }//setPositiveButton
            alertDialog.show()
        }//btnGuardar

    }//onCreate

    fun mensaje(s: String) {
        Toast.makeText(this, s, Toast.LENGTH_LONG).show()
    }//mensaje

    fun dialogo(s: String) {
        androidx.appcompat.app.AlertDialog.Builder(this)
            .setTitle("Atención").setMessage(s)
            .setPositiveButton("Ok") { d, i -> }
            .show()
    }//dialogo


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == SELECT_PHOTO && resultCode == Activity.RESULT_OK && data !== null) {
            val pickedImage = data.data
            imgSelect.setImageURI(pickedImage)
        }//if
    }//onActivityResult

}//class
