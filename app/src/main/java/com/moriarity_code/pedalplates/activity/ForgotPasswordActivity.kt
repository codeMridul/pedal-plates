

package com.moriarity_code.pedalplates.activity

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.moriarity_code.pedalplates.R

class ForgotPasswordActivity : AppCompatActivity() {
    lateinit var etMobileNumber: EditText
    lateinit var etEmailId: EditText
    lateinit var btnNext: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        etMobileNumber = findViewById(R.id.etMobileNumber)
        etEmailId = findViewById(R.id.etEmailId)
        btnNext = findViewById(R.id.btnNext)

        btnNext.setOnClickListener {
            val intent = Intent(this@ForgotPasswordActivity, ResetPasswordActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onBackPressed() {
        val intent = Intent(this@ForgotPasswordActivity, LoginActivity::class.java)
        startActivity(intent)
    }
}
