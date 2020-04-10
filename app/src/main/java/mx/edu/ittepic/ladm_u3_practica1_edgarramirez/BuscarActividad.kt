package mx.edu.ittepic.ladm_u3_practica1_edgarramirez

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_buscar.*
import kotlinx.android.synthetic.main.activity_buscar.btnBuscar
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception
import java.util.*

class BuscarActividad : AppCompatActivity() {
    var listaID = ArrayList<String>()
    var posicion=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buscar)
        cargarCombo()

        btnBuscar.setOnClickListener {

            when (posicion) {
                0->{mensaje("SELECCIONE UNA OPCION")}
                1 -> {
                    cargarxTodos()
                }//1
                2->{
                    cargarxIdAct()
                }//2
                3->{
                    cargarxDescripcion()
                }//3
                4->{
                    cargarxFechaCaptura()
                }//4
                5->{
                    cargarxFechaEntrega()
                }//5
            }//when

        }//btnBuscar
        cargarxTodos()


    }//onCreate

    fun cargarCombo() {
        // access the items of the list
        val seleccion = resources.getStringArray(R.array.seleccion)

        // access the spinner
        if (spinner != null) {
            val adapter = ArrayAdapter(
                this,
                android.R.layout.simple_spinner_dropdown_item, seleccion
            )
            spinner.adapter = adapter

            spinner.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View, position: Int, id: Long) {
                    posicion=position
                    when (position) {
                        0->{
                        }
                        1 -> {
                            mensaje("MOSTRAR TODO")
                        }//1
                        2->{
                            mensaje("BUSCAR POR EL ID DE LA ACTIVIDAD")
                        }//2
                        3->{
                            mensaje("BUSCAR POR LA DESCRIPCION")
                        }//3
                        4->{
                            mensaje("BUSCAR POR LA FECHA DE CAPTURA")
                        }//4
                        5->{
                            mensaje("BUSCAR POR LA FECHA DE ENTREGA")
                        }//5
                    }//when

                }//onItemSelected

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }//onNothingSelected
            }//onItemSelectedListener
        }//if
    }//cargarcombo

    fun cargarxTodos() {
        try {
            var conexion = Actividad("", "", "")
            conexion.asignarPuntero(this)
            var data = conexion.mostrarTodo()
            if (data.size == 0) {
                if (conexion.error == 3) {
                    var vector = Array<String>(data.size, { "" })
                    atencion("NO SE ENCONTRO ALGUN RESULTADO")
                    lista.adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, vector)
                }//if
                return
            }//if
            var total = data.size - 1
            var vector = Array<String>(data.size, { "" })
            listaID = ArrayList<String>()
            (0..total).forEach {
                var actividades = data[it]
                var item =
                    "Id: " + actividades.id_actividad + "\nDescripción: " + actividades.descripcion + "\nFecha de Captura: " + actividades.fechaCaptura + "\nFecha de Entrega: " + actividades.fechaEntrega
                vector[it] = item
                listaID.add(actividades.id_actividad.toString())
            }//forEach
            lista.adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, vector)

            lista.setOnItemClickListener { parent, view, position, id ->
                var con = Actividad("", "", "")
                con.asignarPuntero(this)
                var actiEncontrada = con.buscar(listaID[position])
                if (con.error == 4) {
                    atencion("ERROR, NO FUE ENCONTRADO EL ID")
                    return@setOnItemClickListener
                }//if
                AlertDialog.Builder(this)
                    .setTitle("¿QUE QUIERES HACER?")
                    .setMessage("Id: ${actiEncontrada.id_actividad}\nDescripción: ${actiEncontrada.descripcion}\nFecha de captura:${actiEncontrada.fechaCaptura}\nFecha de entrega:${actiEncontrada.fechaEntrega}")
                    .setPositiveButton("ABRIR") { d, i ->
                        cargarEnOtroActivity(actiEncontrada)
                    }
                    .setNeutralButton("CANCELAR") { d, i -> }
                    .show()
            }//setOnItem

        } catch (e: Exception) {
            atencion(e.message.toString())
        }
    }//cargarxTodos

    fun cargarxIdAct() {
        try {
            var conexion = Actividad("", "", "")
            conexion.asignarPuntero(this)
            var data = conexion.mostrarxIdAct(editBusqueda.text.toString())
            if (data.size == 0) {
                if (conexion.error == 3) {
                    atencion("NO SE HA ENCONTRADO ALGUN RESULTADO ")
                }//if
                return
            }//if
            var total = data.size - 1
            var vector = Array<String>(data.size, { "" })
            listaID = ArrayList<String>()
            (0..total).forEach {
                var actividades = data[it]
                var item =
                    "Id: " + actividades.id_actividad + "\nDescripción: " + actividades.descripcion + "\nFecha de Captura: " + actividades.fechaCaptura + "\nFecha de Entrega: " + actividades.fechaEntrega
                vector[it] = item
                listaID.add(actividades.id_actividad.toString())
            }//forEach
            lista.adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, vector)

            lista.setOnItemClickListener { parent, view, position, id ->
                var con = Actividad("", "", "")
                con.asignarPuntero(this)
                var actiEncontrada = con.buscar(listaID[position])
                if (con.error == 4) {
                    atencion("ERROR, NO FUE ENCONTRADO EL ID")
                    return@setOnItemClickListener
                }//if
                AlertDialog.Builder(this)
                    .setTitle("¿QUE QUIERES HACER?")
                    .setMessage("Id: ${actiEncontrada.id_actividad}\nDescripción: ${actiEncontrada.descripcion}\nFecha de captura:${actiEncontrada.fechaCaptura}\nFecha de entrega:${actiEncontrada.fechaEntrega}")
                    .setPositiveButton("ABRIR") { d, i ->
                        cargarEnOtroActivity(actiEncontrada)
                    }
                    .setNeutralButton("CANCELAR") { d, i -> }
                    .show()
            }//setOnItem

        } catch (e: Exception) {
            atencion(e.message.toString())
        }
    }//cargarxIdAct

    fun cargarxDescripcion() {
        try {
            var conexion = Actividad("", "", "")
            conexion.asignarPuntero(this)
            var data = conexion.mostrarxDescripcion(editBusqueda.text.toString())
            if (data.size == 0) {
                if (conexion.error == 3) {
                    atencion("NO SE ENCONTRO ALGUN RESULTADO")
                }//if
                return
            }//if
            var total = data.size - 1
            var vector = Array<String>(data.size, { "" })
            listaID = ArrayList<String>()
            (0..total).forEach {
                var actividades = data[it]
                var item =
                    "Id: " + actividades.id_actividad + "\nDescripción: " + actividades.descripcion + "\nFecha de Captura: " + actividades.fechaCaptura + "\nFecha de Entrega: " + actividades.fechaEntrega
                vector[it] = item
                listaID.add(actividades.id_actividad.toString())
            }//forEach
            lista.adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, vector)

            lista.setOnItemClickListener { parent, view, position, id ->
                var con = Actividad("", "", "")
                con.asignarPuntero(this)
                var actiEncontrada = con.buscar(listaID[position])
                if (con.error == 4) {
                    atencion("ERROR, NO FUE ENCONTRADO EL ID")
                    return@setOnItemClickListener
                }//if
                AlertDialog.Builder(this)
                    .setTitle("¿QUE QUIERES HACER?")
                    .setMessage("Id: ${actiEncontrada.id_actividad}\nDescripción: ${actiEncontrada.descripcion}\nFecha de captura:${actiEncontrada.fechaCaptura}\nFecha de entrega:${actiEncontrada.fechaEntrega}")
                    .setPositiveButton("ABRIR") { d, i ->
                        cargarEnOtroActivity(actiEncontrada)
                    }
                    .setNeutralButton("CANCELAR") { d, i -> }
                    .show()
            }//setOnItem

        } catch (e: Exception) {
            atencion(e.message.toString())
        }
    }//cargarxDescripcion

    fun cargarxFechaCaptura() {
        try {
            var conexion = Actividad("", "", "")
            conexion.asignarPuntero(this)
            var data = conexion.mostrarxFechaCaptura(editBusqueda.text.toString())
            if (data.size == 0) {
                if (conexion.error == 3) {
                    atencion("NO SE ENCONTRO ALGUN RESULTADO")
                }//if
                return
            }//if
            var total = data.size - 1
            var vector = Array<String>(data.size, { "" })
            listaID = ArrayList<String>()
            (0..total).forEach {
                var actividades = data[it]
                var item =
                    "Id: " + actividades.id_actividad + "\nDescripción: " + actividades.descripcion + "\nFecha de Captura: " + actividades.fechaCaptura + "\nFecha de Entrega: " + actividades.fechaEntrega
                vector[it] = item
                listaID.add(actividades.id_actividad.toString())
            }//forEach
            lista.adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, vector)

            lista.setOnItemClickListener { parent, view, position, id ->
                var con = Actividad("", "", "")
                con.asignarPuntero(this)
                var actiEncontrada = con.buscar(listaID[position])
                if (con.error == 4) {
                    atencion("ERROR, NO FUE ENCONTRADO EL ID")
                    return@setOnItemClickListener
                }//if
                AlertDialog.Builder(this)
                    .setTitle("¿QUE QUIERES HACER?")
                    .setMessage("Id: ${actiEncontrada.id_actividad}\nDescripción: ${actiEncontrada.descripcion}\nFecha de captura:${actiEncontrada.fechaCaptura}\nFecha de entrega:${actiEncontrada.fechaEntrega}")
                    .setPositiveButton("ABRIR") { d, i ->
                        cargarEnOtroActivity(actiEncontrada)
                    }
                    .setNeutralButton("CANCELAR") { d, i -> }
                    .show()
            }//setOnItem

        } catch (e: Exception) {
            atencion(e.message.toString())
        }
    }//cargarxFechaCaptura

    fun cargarxFechaEntrega() {
        try {
            var conexion = Actividad("", "", "")
            conexion.asignarPuntero(this)
            var data = conexion.mostrarxFechaEntrega(editBusqueda.text.toString())
            if (data.size == 0) {
                if (conexion.error == 3) {
                    atencion("NO SE ENCONTRO ALGUN RESULTADO")
                }//if
                return
            }//if
            var total = data.size - 1
            var vector = Array<String>(data.size, { "" })
            listaID = ArrayList<String>()
            (0..total).forEach {
                var actividades = data[it]
                var item =
                    "Id: " + actividades.id_actividad + "\nDescripción: " + actividades.descripcion + "\nFecha de Captura: " + actividades.fechaCaptura + "\nFecha de Entrega: " + actividades.fechaEntrega
                vector[it] = item
                listaID.add(actividades.id_actividad.toString())
            }//forEach
            lista.adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, vector)

            lista.setOnItemClickListener { parent, view, position, id ->
                var con = Actividad("", "", "")
                con.asignarPuntero(this)
                var actiEncontrada = con.buscar(listaID[position])
                if (con.error == 4) {
                    atencion("ERROR, NO FUE ENCONTRADO EL ID")
                    return@setOnItemClickListener
                }//if
                AlertDialog.Builder(this)
                    .setTitle("¿QUE QUIERES HACER?")
                    .setMessage("Id: ${actiEncontrada.id_actividad}\nDescripción: ${actiEncontrada.descripcion}\nFecha de captura:${actiEncontrada.fechaCaptura}\nFecha de entrega:${actiEncontrada.fechaEntrega}")
                    .setPositiveButton("ABRIR") { d, i ->
                        cargarEnOtroActivity(actiEncontrada)
                    }
                    .setNeutralButton("CANCELAR") { d, i -> }
                    .show()
            }//setOnItem

        } catch (e: Exception) {
            atencion(e.message.toString())
        }
    }//cargarxFechaEntrega

    fun mensaje(s: String) {
        Toast.makeText(this, s, Toast.LENGTH_LONG).show()
    }//mensaje

    fun atencion(s: String) {
        AlertDialog.Builder(this)
            .setTitle("Atención").setMessage(s)
            .setPositiveButton("Ok") { d, i -> }
            .show()
    }//atencion

    private fun cargarEnOtroActivity(a: Actividad) {
        var intento = Intent(this, ShowActividad::class.java)
        intento.putExtra("descripcion", a.descripcion)
        intento.putExtra("fechaCaptura", a.fechaCaptura)
        intento.putExtra("fechaEntrega", a.fechaEntrega)
        intento.putExtra("id_actividad", a.id_actividad)
        startActivityForResult(intento, 0)


    }//cargarEnOtroActivity

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        cargarxTodos()
    }

}//class