package com.moriarity_code.pedalplates.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.moriarity_code.pedalplates.R

class OrderSuccessActivity : AppCompatActivity() {
    private lateinit var btnContinue: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_success)

        btnContinue = findViewById(R.id.btnContinue)
        btnContinue.setOnClickListener {
            val intent = Intent(this@OrderSuccessActivity, HomeActivity::class.java)
            startActivity(intent)
            this@OrderSuccessActivity.finish()
        }
    }

    override fun onBackPressed() {
        val intent = Intent(this@OrderSuccessActivity, HomeActivity::class.java)
        startActivity(intent)
        this@OrderSuccessActivity.finish()
    }
}
