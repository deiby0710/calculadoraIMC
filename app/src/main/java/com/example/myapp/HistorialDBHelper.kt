package com.example.myapp

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class HistorialDBHelper(context: Context) :
    SQLiteOpenHelper(context, "HistorialIMC.db", null, 1) {

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = """
            CREATE TABLE historial (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                usuario TEXT,
                fecha TEXT,
                hora TEXT,
                peso REAL,
                estatura REAL,
                imc REAL
            )
        """
        db.execSQL(createTable)
    }


    // Se ejecuta si cambiamos la version de la DB
    // Borra la tabla historial y la vuelve a crear
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS historial")
        onCreate(db)
    }

    fun insertarHistorial(usuario: String, fecha: String, hora: String, peso: Double, estatura: Double, imc: Double) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("usuario", usuario)
            put("fecha", fecha)
            put("hora", hora)
            put("peso", peso)
            put("estatura", estatura)
            put("imc", imc)
        }
        db.insert("historial", null, values)
    }

    fun obtenerHistorial(): List<Map<String, Any>> {
        val lista = mutableListOf<Map<String, Any>>()
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM historial ORDER BY id DESC", null)
        if (cursor.moveToFirst()) {
            do {
                val item = mapOf(
                    "id" to cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                    "usuario" to cursor.getString(cursor.getColumnIndexOrThrow("usuario")),
                    "fecha" to cursor.getString(cursor.getColumnIndexOrThrow("fecha")),
                    "hora" to cursor.getString(cursor.getColumnIndexOrThrow("hora")),
                    "peso" to cursor.getDouble(cursor.getColumnIndexOrThrow("peso")),
                    "estatura" to cursor.getDouble(cursor.getColumnIndexOrThrow("estatura")),
                    "imc" to cursor.getDouble(cursor.getColumnIndexOrThrow("imc"))
                )
                lista.add(item)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return lista
    }
}
