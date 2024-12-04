package com.example.pcallerc
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class ForgotPasswordFragment : Fragment() {

    private lateinit var editTextEmail: EditText
    private lateinit var buttonResetPassword: Button
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_forgot_password, container, false)

        editTextEmail = view.findViewById(R.id.editTextEmail)
        buttonResetPassword = view.findViewById(R.id.buttonResetPassword)
        auth = FirebaseAuth.getInstance()

        buttonResetPassword.setOnClickListener {
            val email = editTextEmail.text.toString().trim()

            if (email.isNotEmpty()) {
                resetPassword(email)
            } else {
                editTextEmail.error = "Enter your email"
            }
        }

        return view
    }

    private fun resetPassword(email: String) {
        auth.sendPasswordResetEmail(email)
            .addOnCompleteListener{ task ->
                if (task.isSuccessful) {
                    // Email sent successfully
                    // Show a success message
                    showToast("Password reset email sent successfully")

                    // Optionally, you can navigate to a success message or login screen here
                } else {
                    // If the email is not registered or other errors occur
                    // Show appropriate error message or handle the error
                    showToast("Failed to send password reset email. Please check the email address.")
                }
            }
        showToast("Password reset email sent successfully")
    }

    private fun showToast(message: String) {
        // Create and show a toast
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
    // Add the ForgotPasswordFragment to the activity
}
