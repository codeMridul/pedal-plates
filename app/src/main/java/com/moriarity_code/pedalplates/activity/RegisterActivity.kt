package com.moriarity_code.pedalplates.activity

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.moriarity_code.pedalplates.R
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {
    lateinit var etName: EditText
    lateinit var etMobileNumber: EditText
    lateinit var etEmailId: EditText
    lateinit var etDelAddress: EditText
    lateinit var etPassword: EditText
    lateinit var etConPassword: EditText
    lateinit var btnRegister: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        setToolbar()

        etName = findViewById(R.id.etName)
        etMobileNumber = findViewById(R.id.etMobileNumber)
        etEmailId = findViewById(R.id.etEmailId)
        etDelAddress = findViewById(R.id.etDeliveryAddress)
        etPassword = findViewById(R.id.etPassword)
        etConPassword = findViewById(R.id.etConPassword)
        btnRegister = findViewById(R.id.btnRegister)


        btnRegister.setOnClickListener {
            Toast.makeText(
                this@RegisterActivity,
                "Registered",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onSupportNavigateUp(): Boolean {//this add functionality to the back arrow in the Action Bar
        val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
        startActivity(intent)
        finish()
        return true
    }

    private fun setToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Register"
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onBackPressed() {
        val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
        startActivity(intent)
    }
}
