package com.example.pcallerc

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Phone
import android.widget.Toast
import com.example.pcallerc.databinding.ActivityMainBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var databaseReference: DatabaseReference
    //private var lastSearchedPhone: String = "" // เพิ่มตัวแปรเพื่อเก็บเบอร์โทรที่ค้นหาล่าสุด

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.houseicon.setOnClickListener {
            val intent = Intent(this@MainActivity, HomeeActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.searchButton.setOnClickListener {
            val searchPhone: String = binding.searchPhone.text.toString()
            if (searchPhone.isNotEmpty()){
                readData(searchPhone)
            }else{
                Toast.makeText(this,"กรุณากรอกเบอร์โทรที่สงสัย", Toast.LENGTH_SHORT).show()
                binding.readName.text = "  "
                binding.readOperator.text = "  "
                binding.readLocation.text = " "
            }
        }

    }

    private fun readData(phone: String){
        databaseReference = FirebaseDatabase.getInstance().getReference("Phone Directory")
        databaseReference.child(phone).get().addOnSuccessListener {
            if (it.exists()){
                val name = it.child("name").value
                val operator = it.child("operator").value
                val location = it.child("location").value
                Toast.makeText(this, "พบผลลัพธ์ของเบอร์โทรนี้", Toast.LENGTH_SHORT).show()
                //binding.searchPhone.text.clear()
                binding.readName.text = name.toString()
                binding.readOperator.text = operator.toString()
                binding.readLocation.text = location.toString()

                // บันทึกเบอร์โทรที่ค้นหาล่าสุด
                //lastSearchedPhone = phone
            }else{
                Toast.makeText(this, "ไม่พบเบอร์ที่คุณค้นหา", Toast.LENGTH_SHORT).show()
                binding.readName.text = "  "
                binding.readOperator.text = "  "
                binding.readLocation.text = " "
            }
        }.addOnFailureListener {
            Toast.makeText(this, "มีบางอย่างผิดปกติ", Toast.LENGTH_SHORT).show()
        }
    }
}