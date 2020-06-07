package com.moriarity_code.pedalplates.activity

import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.moriarity_code.pedalplates.R

class ResetPasswordActivity : AppCompatActivity() {
    lateinit var etOTP: EditText
    lateinit var etPassword: EditText
    lateinit var etConfPassword: EditText
    lateinit var btnSubmit: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_password)

        etOTP = findViewById(R.id.etOTP)
        etPassword = findViewById(R.id.etPassword)
        etConfPassword = findViewById(R.id.etConPassword)
        btnSubmit = findViewById(R.id.btnSubmit)


        btnSubmit.setOnClickListener {
            Toast.makeText(
                this@ResetPasswordActivity,
                "Submit button Pressed",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}
