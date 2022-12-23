package com.example.resa_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.core.content.edit
import com.example.resa_app.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class ActivityLogin : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding
    lateinit var auth : FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        binding  = ActivityLoginBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()

        if(ShPref.instance.getEmail() != "" && ShPref.instance.getPassword() != ""){
            loginFirebase(ShPref.instance.getEmail(), ShPref.instance.getPassword())
        }

        binding.tvToRegister.setOnClickListener{
            val intent = Intent(this, ActivityRegister::class.java)
            startActivity(intent)
        }
        binding.btnLogin.setOnClickListener{
            val email = binding.edtEmailLogin.text.toString()
            val password = binding.edtPasswordLogin.text.toString()

            if(email.isEmpty()){
                binding.edtEmailLogin.error = "Email Harus Diisi"
                binding.edtEmailLogin.requestFocus()
                return@setOnClickListener
            }
            if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                binding.edtEmailLogin.error = "Email Tidak Valid"
                binding.edtEmailLogin.requestFocus()
                return@setOnClickListener
            }
            if(password.isEmpty()){
                binding.edtPasswordLogin.error = "Password Harus Diisi"
                binding.edtPasswordLogin.requestFocus()
                return@setOnClickListener
            }

            loginFirebase(email, password)
        }
    }

    private fun loginFirebase(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this){
                if(it.isSuccessful)
                {
                    if (email != ShPref.instance.getEmail() && password != ShPref.instance.getPassword()){
                        saveData()
                        Toast.makeText(this, "Akun Disimpan Di Local", Toast.LENGTH_SHORT).show()
                    } else{
                        Toast.makeText(this, "Selamat Datang $email", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, MainActivity::class.java)
                    }
                } else {
                    Toast.makeText(this,"${it.exception?.message}",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun saveData()
    {
        val preferences = getSharedPreferences("KEY_DATA", MODE_PRIVATE)
        preferences.edit {
            putString("email", binding.edtEmailLogin.text.toString())
            putString("password", binding.edtPasswordLogin.text.toString())
        }
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}