package com.moriarity_code.pedalplates.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.moriarity_code.pedalplates.R

class OrderSuccessActivity : AppCompatActivity() {
    private lateinit var btnContinue: Button
    private lateinit var txtNo: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_success)
        val total = intent.getStringExtra("total")
        txtNo = findViewById(R.id.txtNo)
        txtNo.text =
            "Your order will never reach you.\uD83D\uDE02\nSit back at home and cook for Yourself.\uD83D\uDC69\u200D\uD83C\uDF73\nKeep Rs. $total ready for smooth payment"
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
