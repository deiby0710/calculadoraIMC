package com.example.myapp

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val editTextName: EditText = findViewById(R.id.inputName)
        val buttonRegister: Button = findViewById(R.id.btnRegister)

        buttonRegister.setOnClickListener {
            val name = editTextName.text.toString().trim()

            if (name.isNotEmpty()) {
                // Guardar el nombre en SharedPreferences
                val prefs: SharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE)
                val editor = prefs.edit()
                editor.putString("userName", name)
                editor.apply()

                // Pasar a MainActivity (calculadora IMC)
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                editTextName.error = "Por favor ingresa tu nombre"
            }
        }
        // 5. Botón de volver
        val btnVolver: ImageButton = findViewById(R.id.btnBack)
        btnVolver.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}