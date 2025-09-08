package com.example.myapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.content.SharedPreferences
import android.content.Intent
import java.text.SimpleDateFormat
import java.util.Date
// Importamos los botones y widgets
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.ImageButton

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        val editTextPeso: EditText = findViewById(R.id.inputPeso)
        val editTextEstatura: EditText = findViewById(R.id.inputEstatura)
        val button: Button = findViewById(R.id.btn1)
        val textView: TextView = findViewById(R.id.textView2)
        val textWelcome: TextView = findViewById(R.id.textViewSaludo)

        // Capturamos el nombre y lo mostramos
        val prefs: SharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE)
        val userName = prefs.getString("userName", "Usuario") ?: "Usuario"

        textWelcome.text = getString(R.string.txt_hola,userName)

        // Cambiar nombre

        val btnEditarUsuario: ImageButton = findViewById(R.id.btnEditarUsuario)

        btnEditarUsuario.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
        // Guardar en SQLite
        val dbHelper = HistorialDBHelper(this)
        // Boton principal
        button.setOnClickListener {
            val peso = editTextPeso.text.toString().toDoubleOrNull()
            val estatura = editTextEstatura.text.toString().toDoubleOrNull()
            // Obtener fecha y hora actuales
            val fecha = SimpleDateFormat("yyyy-MM-dd").format(Date())
            val hora = SimpleDateFormat("HH:mm:ss").format(Date())
            if (peso != null && estatura != null && estatura > 0) {
                // Calcular IMC
                val imc = peso / (estatura * estatura)
                val imcDosDecimales = String.format("%.2f", imc) // devuelve String

                // Mostrar resultado con 2 decimales
//                textView.text = "Tu IMC es: %.2f".format(imc)
                textView.text = getString(R.string.txt_tuIMC, imcDosDecimales.toString())
                // Clasificación del IMC (opcional)
                val clasificacion = when {
                    imc < 18.5 -> getString(R.string.txt_bajoPeso)
                    imc < 24.9 -> getString(R.string.txt_normal)
                    imc < 29.9 -> getString(R.string.txt_sobrepeso)
                    else -> getString(R.string.txt_obecidad)
                }

                // Guardamos en sqlite
                dbHelper.insertarHistorial(userName, fecha, hora, peso, estatura, imcDosDecimales.toDouble())

                textView.append("\n" + getString(R.string.txt_clasificacion, clasificacion))
            } else {
                textView.text = getString(R.string.txt_val1)
            }
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // HISTORIAL
        val btnHistorial: ImageButton = findViewById(R.id.btnHistorial)
        btnHistorial.setOnClickListener {
            val intent = Intent(this, HistorialActivity::class.java)
            startActivity(intent)
        }
    }
}