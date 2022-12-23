package com.example.resa_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.resa_app.databinding.ActivityTambahBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ActivityTambah : AppCompatActivity() {
    lateinit var binding: ActivityTambahBinding
    val db = Firebase.firestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTambahBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val editNominal = binding.nominalPinjaman.text
        val  editTenor = binding.tenorBulan.text

        binding.btnSimulasi.setOnClickListener{
            binding.tfPinjaman.setText(editNominal.toString())
            binding.tfTenor.setText(editTenor.toString())
            hitungTenor(editNominal.toString().toInt(), editTenor.toString().toInt())
        }

        binding.btnSubmit.setOnClickListener {
            if (editNominal.isEmpty()){
                Toast.makeText(this, "Nominal Harus Diisi", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val kredit = hashMapOf(
                "Nominal" to editNominal.toString(),
                "Tenor" to editTenor.toString(),
                "Angsuran" to hitungTenor(editNominal.toString().toInt(), editTenor.toString().toInt())
            )

            db.collection("kredits").add(kredit).addOnSuccessListener{
                    documentReference-> Log.d("TAG", "DocumentSnapshot : ${documentReference.id}")
                Toast.makeText(this,"Data Berhasil Ditambahkan", Toast.LENGTH_SHORT).show()

                val intent = Intent(this, ActivityView::class.java)
                intent.putExtra("idDoc", documentReference.id)
                startActivity(intent)
            }
                .addOnFailureListener{
                    e->Log.w("TAG", "Data Gagal Ditambahkan", e)
                }
        }
    }

    fun hitungTenor(nominal:Int, tenor:Int):Double{
        val hasil = (nominal*0.5)/tenor
        binding.tfAngsuran.setText(hasil.toString())
        return hasil
    }
}