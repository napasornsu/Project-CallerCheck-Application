package com.example.pcallerc

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.pcallerc.databinding.ActivityUploaddBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class UploaddActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUploaddBinding
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUploaddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.houseicon.setOnClickListener {
            val intent = Intent(this@UploaddActivity, HomeeActivity::class.java)
            startActivity(intent)
            finish()
        }

        setupSaveButtonListener()
    }

    private fun setupSaveButtonListener() {
        binding.saveButton.setOnClickListener {
            val name = binding.uploadName.text.toString()
            val operator = binding.uploadOperator.text.toString()
            val location = binding.uploadLoaction.text.toString()
            val phone = binding.uploadPhone.text.toString()

            if (name.isNotEmpty() && operator.isNotEmpty() && location.isNotEmpty() && phone.isNotEmpty()) {
                // บันทึกข้อมูลเมื่อข้อมูลถูกกรอกครบ
                saveUserDataToFirebase(name, operator, location, phone)
            } else {
                // แสดง AlertDialog บันทึกล้มเหลว เนื่องจากข้อมูลไม่ครบถ้วน
                showAlertDialog("Error", "กรุณากรอกข้อมูลให้ครบทุกช่อง")
            }
        }
    }

    private fun saveUserDataToFirebase(name: String, operator: String, location: String, phone: String) {
        databaseReference = FirebaseDatabase.getInstance().getReference("Phone Directory")
        val users = UserData(name, operator, location, phone)
        databaseReference.child(phone).setValue(users).addOnSuccessListener {
            // แสดง AlertDialog บันทึกสำเร็จ
            showAlertDialog("Saved!!", "บันทึกข้อมูลเรียบร้อย",
                positiveAction = {
                    val mainIntent = Intent(this@UploaddActivity, HomeeActivity::class.java)
                    startActivity(mainIntent)
                    finish()
                },
                negativeAction = {
                    finishAffinity()
                }
            )


        }.addOnFailureListener {
            // แสดง AlertDialog บันทึกล้มเหลว
            showAlertDialog("Error", "เกิดข้อผิดพลาดในการบันทึกข้อมูล")
        }
    }

    private fun showAlertDialog(title: String, message: String, positiveAction: () -> Unit = {}) {
        AlertDialog.Builder(this@UploaddActivity)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("OK") { _, _ -> positiveAction.invoke() }
            .show()
    }

    private fun showAlertDialog(
        title: String,
        message: String,
        positiveAction: () -> Unit = {},
        negativeAction: () -> Unit = {}
    ) {
        AlertDialog.Builder(this@UploaddActivity)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("กลับหน้าหลัก") { _, _ -> positiveAction.invoke() }
            .setNegativeButton("ออกจากแอป") { _, _ -> negativeAction.invoke() }
            .show()
    }
}
