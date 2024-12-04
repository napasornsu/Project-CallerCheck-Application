package com.example.pcallerc

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class ResulttActivity : AppCompatActivity() {
    var mAuth: FirebaseAuth? = null
    var mAuthListener: FirebaseAuth.AuthStateListener? = null
    private val TAG:String = "Resultt Activity"
    var userEmail: TextView? = null
    var uidUser : TextView? = null
    var singout : Button? = null
    var backRe: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resultt)
        init()
        //ดึงค่าจาก Firebase มาใส่ใน mAuth
        mAuth = FirebaseAuth.getInstance()
        val user = mAuth!!.currentUser
        //นําค่ามาใส่ลงใน TextView ที5สร้างขึIน
        userEmail?.text ="Email : "+ user!!.email
        uidUser?.text = "UID : " + user.uid
        mAuthListener = FirebaseAuth.AuthStateListener { firebaseAuth ->
            val users = firebaseAuth.currentUser
            if (users == null) {
                startActivity(
                    Intent(
                        this@ResulttActivity,
                        LoginActivity::class.java
                    )
                )
                finish()
            }
        }
        // การทํางานของปุ่ ม Sign out
        singout?.setOnClickListener {
            mAuth!!.signOut()
            Toast.makeText(this,"Signed out!", Toast.LENGTH_LONG).show()
            Log.d(TAG, "Signed out!")
            startActivity(
                Intent(this@ResulttActivity,
                MainActivity::class.java)
            )
            finish()
        }
        //กรณีกดปุ่ ม Back
//        backRe?.setOnClickListener { onBackPressed() }
        backRe?.setOnClickListener {
            val intent = Intent(this@ResulttActivity, HomeeActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
    override fun onStart() {
        super.onStart()
        mAuth!!.addAuthStateListener { mAuthListener }
    }
    override fun onStop() {
        super.onStop()
        if (mAuthListener != null) {
            mAuth!!.removeAuthStateListener { mAuthListener }
        }
    }
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) { moveTaskToBack(true) }
        return super.onKeyDown(keyCode, event)
    }
    fun init() {
        userEmail = findViewById(R.id.result_emailData)
        uidUser = findViewById(R.id.result_uidData)
        singout = findViewById(R.id.result_signOutBtn)
        backRe = findViewById(R.id.result_backBtn)
    }
}