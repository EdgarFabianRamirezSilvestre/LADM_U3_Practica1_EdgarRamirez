package mx.edu.ittepic.ladm_u3_practica1_edgarramirez

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    var c = Calendar.getInstance()
    var año = c.get(Calendar.YEAR)
    var mes = c.get(Calendar.MONTH)
    var dia = c.get(Calendar.DAY_OF_MONTH)
    var fecha = "${año}/${mes + 1}/${dia}"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editCaptura.setOnClickListener {

            var c = Calendar.getInstance()
            var año = c.get(Calendar.YEAR)
            var mes = c.get(Calendar.MONTH)
            var dia = c.get(Calendar.DAY_OF_MONTH)


            var datePicker = DatePickerDialog(
                    this,
                    DatePickerDialog.OnDateSetListener { view, myear, mmonth, mdayOfMonth ->
                        //setToTextView
                        var fecha = "${myear}/${mmonth + 1}/${mdayOfMonth}"
                        editCaptura.setText(fecha)
                    }, año, mes, dia
            )
            datePicker.show()
        }//editCaptura

        editEntrega.setOnClickListener {

            var c = Calendar.getInstance()
            var año = c.get(Calendar.YEAR)
            var mes = c.get(Calendar.MONTH)
            var dia = c.get(Calendar.DAY_OF_MONTH)


            var datePicker = DatePickerDialog(
                    this,
                    DatePickerDialog.OnDateSetListener { view, myear, mmonth, mdayOfMonth ->
                        //setToTextView
                        var fecha = "${myear}/${mmonth + 1}/${mdayOfMonth}"
                        editEntrega.setText(fecha)
                    },año, mes, dia
            )
            datePicker.show()
        }//fechaEntrega

        btnActividad.setOnClickListener {
            var actividades = Actividad(
                editDescripcion.text.toString(),
                editCaptura.text.toString(),
                editEntrega.text.toString())

            actividades.asignarPuntero(this)
            var resultado = actividades.insertar()

            if (resultado == true) {
                mensaje("!ACTIVIDAD REGISTRADA!")
                editDescripcion.setText("")
                editCaptura.setText("")
                editEntrega.setText("")

            } else {
                when (actividades.error) {
                    1 -> {
                        atencion("!ERROR DE TABLA!, FALLO EN CREACION O CONEXION CON LA BASE DE DATOS")
                    }//1
                    2 -> {
                        atencion("!ERROR DE INSERCION EN LA TABLA!")
                    }//2
                }//when
            }//else
        }//btnActividad

        btnBuscar.setOnClickListener {
            startActivity(Intent(this, BuscarActividad::class.java))
        }//btnBuscar

        btnEvidencia.setOnClickListener {
            startActivity(Intent(this, SelectActividad::class.java))
        }//btnEvidencia

    }//Oncreate

    fun mensaje(s: String) {
        Toast.makeText(this, s, Toast.LENGTH_LONG).show()
    }//mensaje

    fun atencion(s: String) {
        AlertDialog.Builder(this)
                .setTitle("Atención").setMessage(s)
                .setPositiveButton("Ok") { d, i -> }
                .show()
    }//atencion

}//class
