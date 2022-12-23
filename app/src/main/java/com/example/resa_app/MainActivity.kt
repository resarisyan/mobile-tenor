package com.example.resa_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.content.Intent
import androidx.core.content.edit
import com.example.resa_app.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        auth = FirebaseAuth.getInstance()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setContentView(binding.root)

        binding.Simulasi.setOnClickListener {
            val intent = Intent(this, ActivityTambah::class.java)
            startActivity(intent)
        }

        binding.Logout.setOnClickListener{
            auth.signOut()
            val intent = Intent(this, ActivityLogin::class.java)
            val preferences = getSharedPreferences("KEY_DATA", MODE_PRIVATE)
            preferences.edit {
                putString("email", "")
                putString("password", "")
            }
            startActivity(intent)
        }

    }
}