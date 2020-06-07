package com.moriarity_code.pedalplates.activity

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.moriarity_code.pedalplates.R

class LoginActivity : AppCompatActivity() {

    lateinit var etMobileNumber:EditText
    lateinit var etPassword: EditText
    lateinit var btnLogin: TextView
    lateinit var txtForgotPassword: TextView
    lateinit var txtRegister: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        etMobileNumber = findViewById(R.id.etMobileNumber)
        etPassword = findViewById(R.id.etPassword)
        btnLogin = findViewById(R.id.btnLogin)
        txtForgotPassword = findViewById(R.id.txtForgotPassword)
        txtRegister = findViewById(R.id.txtRegister)


        btnLogin.setOnClickListener {
            Toast.makeText(
                this@LoginActivity,
                "Login Button Clicked",
                Toast.LENGTH_SHORT).show()
        }

        txtForgotPassword.setOnClickListener{
            val intent = Intent(this@LoginActivity, ForgotPasswordActivity::class.java)
            startActivity(intent)
        }

        txtRegister.setOnClickListener {
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)
        }

    }

    override fun onPause() {
        super.onPause()
        finish()
    }

    override fun onBackPressed() {
        finish()
    }
}
