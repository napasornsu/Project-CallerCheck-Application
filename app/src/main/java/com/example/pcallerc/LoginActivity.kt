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

class LoginActivity : AppCompatActivity() {
    var mAuth: FirebaseAuth? = null
    private val TAG: String = "Login Activity"
    var loginBtn : Button? = null
    var userEmail: EditText? = null
    var userPass : EditText? = null
    var createUser : Button? = null
    var backLogin : ImageView? = null
    var changebtn : Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        init()
        mAuth = FirebaseAuth.getInstance()

        if (mAuth!!.currentUser != null) {
            startActivity(
                Intent(this@LoginActivity,
                ResulttActivity::class.java)
            )
            finish()
        }
        loginBtn?.setOnClickListener {
            val email = userEmail?.text.toString().trim { it <= ' ' }
            val password = userPass?.text.toString().trim { it <= ' ' }

            // ทําการตรวจสอบก่อนว่ามีข้อมูลหรือไม่ ก่อนทีจะไปตรวจสอบค่า
            if (email.isEmpty()) {
                Toast.makeText(this,"Please enter your email address.",Toast.LENGTH_LONG).show()
                Log.d(TAG, "Email was empty!")
                return@setOnClickListener
            }
            if (password.isEmpty()) {
                Toast.makeText(this,"Please enter your password.",Toast.LENGTH_LONG).show()
                        Log.d(TAG, "Password was empty!")
                return@setOnClickListener
            }
            //ทําการตรวจสอบค่าที5กรอกกับค่าจาก Firebase Authentication
            mAuth!!.signInWithEmailAndPassword(email,
                password).addOnCompleteListener { task ->
                //กรณีที5ไม่ผ่านการตรวจสอบ

                if (!task.isSuccessful) {

                //ตรวจสอบความยาวของ password ว่าน้อยกว่า 6 ไหม
                    if (password.length < 6) {

                        userPass?.error = "Please check your password. Password must have minimum 6 characters."
                        Log.d(TAG, "Enter password less than 6 characters.")
                    } else {
                        Toast.makeText(this,"Authentication Failed: " +
                                task.exception!!.message,Toast.LENGTH_LONG).show()
                        Log.d(TAG, "Authentication Failed: " +
                                task.exception!!.message)
                    }
                } else { //กรณีที5อีเมลและรหัสถูกต้อง
                    Toast.makeText(this,"Sign in successfully!",Toast.LENGTH_LONG).show()
                            Log.d(TAG, "Sign in successfully!")
                    startActivity(Intent(this@LoginActivity,
                        ResulttActivity::class.java))
                    finish()
                }
            }
        }
        //กรณีกดปุ่ ม create account
        createUser?.setOnClickListener {
            startActivity(Intent(this@LoginActivity, RegisterActivity::class.java)) }

        //กรณีกดปุ่ ม Back
//        backLogin?.setOnClickListener { onBackPressed() }
        backLogin?.setOnClickListener {
            val intent = Intent(this@LoginActivity, WaitActivity::class.java)
            startActivity(intent)
            finish()
        }

        changebtn?.setOnClickListener {
            val intent = Intent(this@LoginActivity, ForgotActivity::class.java)
            startActivity(intent)
            finish()
        }



    }
    fun init(){
        userEmail = findViewById(R.id.login_emailEdt)
        userPass = findViewById(R.id.login_passwordEdt)
        loginBtn = findViewById(R.id.login_loginBtn)
        createUser = findViewById(R.id.login_createBtn)
        backLogin = findViewById(R.id.login_backBtn)
        changebtn = findViewById(R.id.login_changep)
    }
}