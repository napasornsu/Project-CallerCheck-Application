package com.example.pcallerc

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {

    var mAuth: FirebaseAuth? = null
    private val TAG: String = "Register Activity"
    var regisEmail: EditText? = null
    var regisPass: EditText? = null
    var createAcc : Button? = null
    var rSignin : Button? = null
    var backR : ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        init()
        mAuth = FirebaseAuth.getInstance()

        if (mAuth!!.currentUser != null) {
            startActivity(
                Intent(this@RegisterActivity,
                ResulttActivity::class.java)
            )
            finish()
        }
        createAcc?.setOnClickListener {
            val email = regisEmail?.text.toString().trim { it <= ' ' }
            val password = regisPass?.text.toString().trim { it <= ' ' }

            //ทําการตรวจสอบก่อนว่ามีข้อมูลหรือไม่
            if (email.isEmpty()) {
                Toast.makeText(this,"Please enter your email address.",
                    Toast.LENGTH_LONG).show()
                Log.d(TAG, "Email was empty!")
                return@setOnClickListener
            }
            if (password.isEmpty()) {
                Toast.makeText(this,"Please enter your password.",Toast.LENGTH_LONG).show()
                        Log.d(TAG, "Password was empty!")
                return@setOnClickListener
            }
            //กรณีที5มีข้อมูล จะทําการตรวจสอบเงื5อนไขอื5น ๆ ก่อนทําการ create user
            mAuth!!.createUserWithEmailAndPassword(email,
                password).addOnCompleteListener { task ->
                if (!task.isSuccessful) {
                    if (password.length < 6) { // ตรวจสอบความยาวของ password
                        Toast.makeText(this,"Password too short! Please enter minimum 6 characters.",Toast.LENGTH_LONG).show()
                                Log.d(TAG, "Enter password less than 6 characters.")
                    } else {
                        Toast.makeText(this,"Authentication Failed: " +
                                task.exception!!.message,Toast.LENGTH_LONG).show()
                        Log.d(TAG, "Authentication Failed: " +
                                task.exception!!.message)
                    }
                } else {
                    Toast.makeText(this,"Create account successfully!",Toast.LENGTH_LONG).show()
                            Log.d(TAG, "Create account successfully!")
                    startActivity(Intent(this@RegisterActivity,
                        ResulttActivity::class.java))
                    finish()
                }
            }
        }
        //กรณีกดปุ่ ม Back
//        backR?.setOnClickListener { onBackPressed() }
        backR?.setOnClickListener {
            val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
        rSignin?.setOnClickListener {
            startActivity(Intent(this@RegisterActivity, LoginActivity::class.java)) }
    }
    fun init(){
        regisEmail = findViewById(R.id.register_emailEdt)
        regisPass = findViewById(R.id.register_passwordEdt)
        createAcc = findViewById(R.id.register_registerBtn)
        rSignin = findViewById(R.id.register_signinBtn)
        backR = findViewById(R.id.register_backBtn)
    }
}