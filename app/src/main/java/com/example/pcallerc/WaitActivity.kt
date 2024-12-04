package com.example.pcallerc

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.google.firebase.auth.FirebaseAuth

class WaitActivity : AppCompatActivity() {

//    private lateinit var mAuth: FirebaseAuth
    var mAuth: FirebaseAuth? = null
    private val TAG: String = "Wait Activity"
    var mainSignin : Button? = null
    var mainUndo : Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wait)

        mainSignin = findViewById(R.id.main_emailBtn)
        mainUndo = findViewById(R.id.wait_undoBtn)

        // เป็นการดึงข้อมูลการ login จาก Firebase Authentication
        mAuth = FirebaseAuth.getInstance()

        //กรณีที5มีการ login ค้างไว้ จะสามารถเข้าหน้า Upload ได้เลย
        if (mAuth!!.currentUser != null) {
            Log.d(TAG, "Continue with: " + mAuth!!.currentUser!!.email)
            // เป็นการสั5งให้ทําการ start activity ส่วนของหน้า upload
            startActivity(
                Intent(this@WaitActivity,
                UploaddActivity::class.java)
            )
            finish()
        }
        //กรณีกดปุ่ ม Back
//        mainUndo?.setOnClickListener { onBackPressed() }
        mainUndo?.setOnClickListener {
            val intent = Intent(this@WaitActivity, HomeeActivity::class.java)
            startActivity(intent)
            finish()
        }
        mainSignin?.setOnClickListener {
            val intent = Intent(this@WaitActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }



    }
}