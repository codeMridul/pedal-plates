package com.moriarity_code.pedalplates.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.moriarity_code.pedalplates.R
import org.json.JSONObject

class ResetPasswordActivity : AppCompatActivity() {
    private lateinit var etOTP: EditText
    private lateinit var etPassword: EditText
    private lateinit var etConfPassword: EditText
    private lateinit var btnSubmit: TextView

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_password)

        etOTP = findViewById(R.id.etOTP)
        etPassword = findViewById(R.id.etPassword)
        etConfPassword = findViewById(R.id.etConPassword)
        btnSubmit = findViewById(R.id.btnSubmit)
        val mobile = intent.getStringExtra("mobile_number")

        sharedPreferences =
            getSharedPreferences(getString(R.string.preference_file_name), Context.MODE_PRIVATE)

        btnSubmit.setOnClickListener {
            val otp = etOTP.text.toString()
            val password = etPassword.text.toString()
            val confPassword = etConfPassword.text.toString()

            if (password == confPassword) {
                val queue = Volley.newRequestQueue(this@ResetPasswordActivity)
                val url = "http://13.235.250.119/v2/reset_password/fetch_result"
                val jsonParams = JSONObject()
                jsonParams.put("mobile_number", mobile)
                jsonParams.put("password", password)
                jsonParams.put("otp", otp)

                val jsonObjectRequest = object : JsonObjectRequest(
                    Request.Method.POST,
                    url,
                    jsonParams,
                    Response.Listener
                    { response ->
                        try {
                            val it = response.getJSONObject("data")
                            val success = it.getBoolean("success")
                            val message = it.getString("successMessage")
                            Toast.makeText(
                                this@ResetPasswordActivity,
                                message,
                                Toast.LENGTH_SHORT
                            ).show()
                            if (success) {
                                val intent =
                                    Intent(this@ResetPasswordActivity, LoginActivity::class.java)
                                startActivity(intent)
                                sharedPreferences.edit().clear().apply()
                                this@ResetPasswordActivity.finish()
                            }
                        } catch (e: Exception) {
                            Toast.makeText(
                                this@ResetPasswordActivity,
                                e.message,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    },
                    Response.ErrorListener {
                        Toast.makeText(
                            this@ResetPasswordActivity,
                            "Could not reach the server!!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                ) {
                    override fun getHeaders(): MutableMap<String, String> {
                        val headers = HashMap<String, String>()
                        headers["Content-Type"] = "application/json"
                        headers["token"] = "ff94a4233d0da6"
                        return headers
                    }
                }
                queue.add(jsonObjectRequest)
            } else {
                Toast.makeText(
                    this@ResetPasswordActivity,
                    "Password Mismatch",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}
