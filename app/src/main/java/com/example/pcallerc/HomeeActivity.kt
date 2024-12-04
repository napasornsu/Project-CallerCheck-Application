package com.example.pcallerc

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.pcallerc.databinding.ActivityHomeeBinding
import com.google.firebase.auth.FirebaseAuth

class HomeeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeeBinding
    private var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mAuth = FirebaseAuth.getInstance()  // ตัวแปร mAuth ต้องถูกกำหนดค่าก่อนใช้งาน

        binding.mainSearch.setOnClickListener {
            val intent = Intent(this@HomeeActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.mainUpload.setOnClickListener {
//            val intent = Intent(this@HomeeActivity, UploaddActivity::class.java)
//            startActivity(intent)
//            finish()

            val intent = if (mAuth?.currentUser == null) {
                Intent(this@HomeeActivity, WaitActivity::class.java)
            } else {
                Intent(this@HomeeActivity, UploaddActivity::class.java)
            }
            startActivity(intent)
            finish()
        }

        binding.homeProfilebtn.setOnClickListener {
            val intent = if (mAuth?.currentUser == null) {
                Intent(this@HomeeActivity, LoginActivity::class.java)
            } else {
                Intent(this@HomeeActivity, ResulttActivity::class.java)
            }
            startActivity(intent)
            finish()
        }


    }
}