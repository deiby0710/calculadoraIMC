package com.example.myapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest

class RegisterActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // Inicializar Firebase Auth
        auth = FirebaseAuth.getInstance()

        val inputName: EditText = findViewById(R.id.inputName)
        val inputEmail: EditText = findViewById(R.id.inputEmail)
        val inputPassword: EditText = findViewById(R.id.inputPassword)
        val btnRegister: Button = findViewById(R.id.btnRegister)
        val btnBack: ImageButton = findViewById(R.id.btnBack)

        // Botón registrar
        btnRegister.setOnClickListener {
            val name = inputName.text.toString().trim()
            val email = inputEmail.text.toString().trim()
            val password = inputPassword.text.toString().trim()

            if (name.isEmpty()) {
                inputName.error = "Ingresa tu nombre"
                return@setOnClickListener
            }
            if (email.isEmpty()) {
                inputEmail.error = "Ingresa tu correo"
                return@setOnClickListener
            }
            if (password.length < 6) {
                inputPassword.error = "La contraseña debe tener al menos 6 caracteres"
                return@setOnClickListener
            }

            // Crear usuario en Firebase
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val user = auth.currentUser
                        // Guardar el nombre en el perfil
                        val profileUpdates = UserProfileChangeRequest.Builder()
                            .setDisplayName(name)
                            .build()

                        user?.updateProfile(profileUpdates)
                            ?.addOnCompleteListener { updateTask ->
                                if (updateTask.isSuccessful) {
                                    Toast.makeText(this, "Usuario registrado ✅", Toast.LENGTH_SHORT).show()
                                    // Ir al login
                                    val intent = Intent(this, Login::class.java)
                                    startActivity(intent)
                                    finish()
                                } else {
                                    Toast.makeText(this, "Error al guardar nombre", Toast.LENGTH_SHORT).show()
                                }
                            }
                    } else {
                        Toast.makeText(this, "Error: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                    }
                }
        }

        // Botón volver
        btnBack.setOnClickListener {
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
            finish()
        }
    }
}
