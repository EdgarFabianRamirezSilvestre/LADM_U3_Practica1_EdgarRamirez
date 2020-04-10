package mx.edu.ittepic.ladm_u3_practica1_edgarramirez

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteQueryBuilder

class BDHelper(
    context: Context?,
    name: String?,
    factory: SQLiteDatabase.CursorFactory?,
    version: Int
) : SQLiteOpenHelper(context, name, factory, version) {

    var error = -1
    var idEvi=0
    var idAct=0
    var img:ByteArray?=null

    @Throws(SQLiteException::class)
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(
            "CREATE TABLE ACTIVIDADES(ID_ACTIVIDAD INTEGER " + "PRIMARY KEY AUTOINCREMENT, DESCRIPCION VARCHAR(2000), " +
                    "FECHACAPTURA DATE, FECHAENTREGA DATE)"
        )

        db?.execSQL(
            "CREATE TABLE EVIDENCIAS(IDEVIDENCIA INTEGER " + "PRIMARY KEY AUTOINCREMENT, ID_ACTIVIDAD INTEGER, " +
                    "FOTO BLOB, FOREIGN KEY(ID_ACTIVIDAD) REFERENCES ACTIVIDADES (ID_ACTIVIDAD))"
        )


    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

}
