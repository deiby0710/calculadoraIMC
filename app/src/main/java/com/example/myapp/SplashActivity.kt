package com.example.myapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.os.Handler
import android.os.Looper
import android.content.Intent
import com.google.firebase.auth.FirebaseAuth

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        // Esperar 2 segundos y abrir MainActivity
        Handler(Looper.getMainLooper()).postDelayed({
            val auth = FirebaseAuth.getInstance()
            val currentUser = auth.currentUser

            if (currentUser != null) {
                // Usuario ya logueado → ir directo a MainActivity
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            } else {
                // No hay usuario logueado → ir a Login
                val intent = Intent(this, Login::class.java)
                startActivity(intent)
            }

            finish() // Cerramos Splash para que no vuelva atrás
        }, 3000) // 2000 ms = 2 segundos
    }
}