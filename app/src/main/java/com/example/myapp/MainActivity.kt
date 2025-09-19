package com.example.myapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import java.text.SimpleDateFormat
import java.util.Date

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // 🔹 Inicializar FirebaseAuth
        auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser

        // Si no hay usuario, volver al login
        if (currentUser == null) {
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
            finish()
            return
        }

        val editTextPeso: EditText = findViewById(R.id.inputPeso)
        val editTextEstatura: EditText = findViewById(R.id.inputEstatura)
        val button: Button = findViewById(R.id.btn1)
        val textView: TextView = findViewById(R.id.textView2)
        val textWelcome: TextView = findViewById(R.id.textViewSaludo)

        // 🔹 Mostrar saludo con el nombre (displayName)
        textWelcome.text = getString(R.string.txt_hola, currentUser.displayName ?: "Usuario")

        // Botón logout (ImageButton con icono que tú definas)
        val btnCerrarSesion: ImageButton = findViewById(R.id.btnCerrarSesion)
        btnCerrarSesion.setOnClickListener {
            auth.signOut()
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
            finish()
        }


        // Guardar en SQLite
        val dbHelper = HistorialDBHelper(this)

        // 🔹 Botón principal (calcular IMC)
        button.setOnClickListener {
            Log.d("DEBUG_IMC", "Holaaaaa 👋")
            val peso = editTextPeso.text.toString().replace(",", ".").toDoubleOrNull()
            val estatura = editTextEstatura.text.toString().replace(",", ".").toDoubleOrNull()
            Log.d("DEBUG_IMC", "Input peso: $peso, Input estatura: $estatura")

            val fecha = SimpleDateFormat("yyyy-MM-dd").format(Date())
            val hora = SimpleDateFormat("HH:mm:ss").format(Date())

            if (peso != null && estatura != null && estatura > 0) {
                val imc = peso / (estatura * estatura)
                val imcDosDecimales = String.format("%.2f", imc)

                textView.text = getString(R.string.txt_tuIMC, imcDosDecimales)

                val clasificacion = when {
                    imc < 18.5 -> getString(R.string.txt_bajoPeso)
                    imc < 24.9 -> getString(R.string.txt_normal)
                    imc < 29.9 -> getString(R.string.txt_sobrepeso)
                    else -> getString(R.string.txt_obecidad)
                }

                // Guardar en sqlite usando el nombre del usuario logueado
                dbHelper.insertarHistorial(
                    currentUser.displayName ?: "Anon",
                    fecha,
                    hora,
                    peso,
                    estatura,
                    imc
                )

                textView.append("\n" + getString(R.string.txt_clasificacion, clasificacion))
                Log.d("DEBUG_IMC", "IMC guardado: $imc")
            } else {
                textView.text = getString(R.string.txt_val1)
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 🔹 Botón Historial
        val btnHistorial: ImageButton = findViewById(R.id.btnHistorial)
        btnHistorial.setOnClickListener {
            val intent = Intent(this, HistorialActivity::class.java)
            startActivity(intent)
        }

    }
}