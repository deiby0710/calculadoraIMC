package com.example.myapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.os.Handler
import android.os.Looper
import android.content.Intent
import android.content.SharedPreferences

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        // Esperar 2 segundos y abrir MainActivity
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

            // Revisar SharedPreferences
            val prefs: SharedPreferences = getSharedPreferences("MyAppPrefs", MODE_PRIVATE)
            val userName = prefs.getString("userName", null)

            // Decidir a dónde ir
            if (userName.isNullOrEmpty()) {
                // No registrado -> ir a RegisterActivity
                startActivity(Intent(this, RegisterActivity::class.java))
            } else {
                // Ya registrado -> ir a MainActivity
                startActivity(Intent(this, MainActivity::class.java))
            }

            // Cerrar Splash
            finish()
        }, 3000) // 2000 ms = 2 segundos
    }
}