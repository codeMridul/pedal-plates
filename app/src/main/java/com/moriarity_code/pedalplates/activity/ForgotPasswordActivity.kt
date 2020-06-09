

package com.moriarity_code.pedalplates.activity

import android.content.Intent
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
import org.json.JSONException
import org.json.JSONObject

class ForgotPasswordActivity : AppCompatActivity() {
    private lateinit var etMobileNumber: EditText
    private lateinit var etEmailId: EditText
    private lateinit var btnNext: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        etMobileNumber = findViewById(R.id.etMobileNumber)
        etEmailId = findViewById(R.id.etEmailId)
        btnNext = findViewById(R.id.btnNext)

        btnNext.setOnClickListener {
            val mobile: String? = etMobileNumber.text.toString()
            val email: String? = etEmailId.text.toString()

            val queue = Volley.newRequestQueue(this@ForgotPasswordActivity)
            val url = "http://13.235.250.119/v2/forgot_password/fetch_result"
            val jsonParams = JSONObject()
            jsonParams.put("mobile_number", mobile)
            jsonParams.put("email", email)

            val jsonObjectRequest = object : JsonObjectRequest(
                Request.Method.POST,
                url,
                jsonParams,
                Response.Listener
                { response ->
                    try {
                        val it = response.getJSONObject("data")
                        val success = it.getBoolean("success")
                        if (success) {
                            if (it.getBoolean("first_try")) {
                                Toast.makeText(
                                    this@ForgotPasswordActivity,
                                    "OTP has been send to your registered email address",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                Toast.makeText(
                                    this@ForgotPasswordActivity,
                                    "OTP has been send to your registered email address once\nUse the same OTP",
                                    Toast.LENGTH_SHORT
                                ).show()

                            }
                            val intent =
                                Intent(
                                    this@ForgotPasswordActivity,
                                    ResetPasswordActivity::class.java
                                )
                            intent.putExtra("mobile_number", mobile)
                            startActivity(intent)
                        } else {
                            Toast.makeText(
                                this@ForgotPasswordActivity,
                                "User not found",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } catch (e: JSONException) {
                        Toast.makeText(
                            this@ForgotPasswordActivity,
                            e.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                },
                Response.ErrorListener {
                    Toast.makeText(
                        this@ForgotPasswordActivity,
                        "Could not reach server!!",
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
        }
    }

    override fun onBackPressed() {
        val intent = Intent(this@ForgotPasswordActivity, LoginActivity::class.java)
        startActivity(intent)
    }
}
