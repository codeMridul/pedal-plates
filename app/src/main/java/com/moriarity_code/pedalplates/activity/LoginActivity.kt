package com.moriarity_code.pedalplates.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.provider.Settings
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.moriarity_code.pedalplates.R
import com.moriarity_code.pedalplates.util.ConnectionManager
import org.json.JSONException
import org.json.JSONObject

class LoginActivity : AppCompatActivity() {

    private lateinit var etMobileNumber: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnLogin: TextView
    private lateinit var txtForgotPassword: TextView
    private lateinit var txtRegister: TextView

    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        sharedPreferences =
            getSharedPreferences(getString(R.string.preference_file_name), Context.MODE_PRIVATE)
        val isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false)
        if (isLoggedIn) {
            val intent = Intent(this@LoginActivity, HomeActivity::class.java)
            startActivity(intent)
            this@LoginActivity.finish()
        }

        etMobileNumber = findViewById(R.id.etMobileNumber)
        etPassword = findViewById(R.id.etPassword)
        btnLogin = findViewById(R.id.btnLogin)
        txtForgotPassword = findViewById(R.id.txtForgotPassword)
        txtRegister = findViewById(R.id.txtRegister)


        btnLogin.setOnClickListener {
            val mobile: String? = etMobileNumber.text.toString()
            val password: String? = etPassword.text.toString()

            val queue = Volley.newRequestQueue(this@LoginActivity)
            val url = "http://13.235.250.119/v2/login/fetch_result"
            val jsonParams = JSONObject()
            jsonParams.put("mobile_number", mobile)
            jsonParams.put("password", password)

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
                            val profileObject = it.getJSONObject("data")
                            with(sharedPreferences.edit())  //We are saving all the data that we receive in response in shared Preferences
                            {
                                putBoolean("isLoggedIn", true).apply()
                                putString("user_id", profileObject.getString("user_id")).apply()
                                putString("name", profileObject.getString("name")).apply()
                                putString("email", profileObject.getString("email")).apply()
                                putString(
                                    "mobile_number",
                                    profileObject.getString("mobile_number")
                                ).apply()
                                putString("address", profileObject.getString("address")).apply()
                            }
                            val intent = Intent(this@LoginActivity, HomeActivity::class.java)
                            startActivity(intent)
                            sharedPreferences.edit().putBoolean("isLoggedIn", true)
                            this@LoginActivity.finish()
                        } else {
                            Toast.makeText(
                                this@LoginActivity,
                                "Wrong Credentials",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } catch (e: JSONException) {
                        Toast.makeText(
                            this@LoginActivity,
                            e.message,
                            Toast.LENGTH_SHORT
                        ).show()

                    }
                },
                Response.ErrorListener {
                    Toast.makeText(
                        this@LoginActivity,
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
        }

        txtForgotPassword.setOnClickListener{
            val intent = Intent(this@LoginActivity, ForgotPasswordActivity::class.java)
            startActivity(intent)
        }

        txtRegister.setOnClickListener {
            if (ConnectionManager().checkConnectivity(this@LoginActivity)) {
                val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
                startActivity(intent)
            } else {
                val dialog = AlertDialog.Builder(this@LoginActivity)
                dialog.setTitle("Failed!!")
                dialog.setMessage("Internet Connection Not Found")
                dialog.setPositiveButton("Open Settings") { _, _ ->
                    val settingIntent = Intent(Settings.ACTION_WIRELESS_SETTINGS)
                    startActivity(settingIntent)
                    this@LoginActivity.finish()
                }
                dialog.setNegativeButton("Cancel") { _, _ ->
                }
                dialog.create()
                dialog.show()
            }

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
