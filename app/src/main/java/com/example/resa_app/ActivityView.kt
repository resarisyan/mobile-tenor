package com.example.resa_app

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.resa_app.databinding.ActivityViewBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class ActivityView : AppCompatActivity() {
    lateinit var binding: ActivityViewBinding
    lateinit var db : FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityViewBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        db = FirebaseFirestore.getInstance()
        db.collection("kredits").document(intent.getStringExtra("idDoc").toString())
            .get()
            .addOnSuccessListener {
                binding.edtNominal.setText(it.data?.get("Nominal").toString())
                binding.edtTenor.setText(it.data?.get("Tenor").toString())
                binding.edtAngsuran.setText(it.data?.get("Angsuran").toString())
                it.data?.get("").toString()
            }
            .addOnFailureListener{
                it.printStackTrace()
                Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
            }

        binding.btnDelete.setOnClickListener {
            delete()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        binding.btnUpdate.setOnClickListener {
            update()
        }
    }

    fun update()
    {
        val editNominal = binding.edtNominal.text
        val editTenor = binding.edtTenor.text

        db.collection("Kredits").document(intent.getStringExtra("idDoc").toString())
            .update(mapOf(
                "Nominal" to editNominal.toString(),
                "Tenor" to editTenor.toString(),
                "Angsuran" to editNominal.toString(),
            ))
            .addOnSuccessListener{
                Log.d(TAG, "Document Update!")
                Toast.makeText(this, "Data Berhasil Diupdate",
                Toast.LENGTH_SHORT).show()

                val intent = Intent(this, ActivityTambah::class.java)
                startActivity(intent)
            }
            .addOnFailureListener{
                e -> Log.w(TAG, "Error Updating", e)
            }
        val intent = Intent(this, ActivityView::class.java)
        startActivity(intent)
    }

    fun delete()
    {
        db.collection("kredits")
            .document(intent.getStringExtra("idDoc").toString())
            .delete()
            .addOnSuccessListener {
                Log.d(ContentValues.TAG, "Document Snapshoot Successfully Deleted!")
            }
            .addOnFailureListener{
                e -> Log.w(ContentValues.TAG, "Error Deleting Document!", e)
            }
    }
}