package com.example.pcallerc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class ForgotActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot)

        // Add the ForgotPasswordFragment to the activity
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, ForgotPasswordFragment())
            .commit()
    }
}