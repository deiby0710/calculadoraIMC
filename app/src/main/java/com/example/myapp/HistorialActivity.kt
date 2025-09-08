package com.example.myapp

import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.content.Intent

class HistorialActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_historial)

        // Ajustar bordes
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 1. Referencia al RecyclerView
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewHistorial)

        // 2. Crear instancia del DBHelper
        val dbHelper = HistorialDBHelper(this)

        // 3. Obtener datos reales de la DB
        val listaHistorial = dbHelper.obtenerHistorial().map {
            Historial(
                usuario = it["usuario"] as String,
                fechaHora = "${it["fecha"]} ${it["hora"]}",
                peso = it["peso"].toString(),
                estatura = it["estatura"].toString(),
                imc = it["imc"].toString()
            )
        }

        // 4. Configurar el RecyclerView con los datos de la DB
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = HistorialAdapter(listaHistorial)

        // 5. Botón de volver
        val btnVolver: ImageButton = findViewById(R.id.btnBack)
        btnVolver.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
