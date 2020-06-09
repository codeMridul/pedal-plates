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
import kotlinx.android.synthetic.main.activity_register.*
import org.json.JSONException
import org.json.JSONObject

class RegisterActivity : AppCompatActivity() {
    lateinit var etName: EditText
    lateinit var etMobileNumber: EditText
    lateinit var etEmailId: EditText
    lateinit var etDelAddress: EditText
    lateinit var etPassword: EditText
    lateinit var etConPassword: EditText
    lateinit var btnRegister: TextView

    lateinit var sharedPreferences: SharedPreferences

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
            val name: String? = etName.text.toString()
            val mobile: String? = etMobileNumber.text.toString()
            val email: String? = etEmailId.text.toString()
            val address: String? = etDelAddress.text.toString()
            val password: String? = etPassword.text.toString()
            val conPassword: String? = etConPassword.text.toString()
            if (name != null && mobile != null && email != null && address != null && password != null && conPassword != null) {
                if (password == conPassword) {
                    val queue = Volley.newRequestQueue(this@RegisterActivity)
                    val url = "http://13.235.250.119/v2/register/fetch_result"
                    val jsonParams = JSONObject()
                    jsonParams.put("name", name)
                    jsonParams.put("mobile_number", mobile)
                    jsonParams.put("password", password)
                    jsonParams.put("address", address)
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
                                    val profileObject = it.getJSONObject("data")
                                    sharedPreferences = getSharedPreferences(
                                        getString(R.string.preference_file_name),
                                        Context.MODE_PRIVATE
                                    )
                                    with(sharedPreferences.edit())  //We are saving all the data that we receive in response in shared Preferences
                                    {
                                        putBoolean("isLoggedIn", true).apply()
                                        putString(
                                            "user_id",
                                            profileObject.getString("user_id")
                                        ).apply()
                                        putString("name", profileObject.getString("name")).apply()
                                        putString("email", profileObject.getString("email")).apply()
                                        putString(
                                            "mobile_number",
                                            profileObject.getString("mobile_number")
                                        ).apply()
                                        putString(
                                            "address",
                                            profileObject.getString("address")
                                        ).apply()
                                    }
                                    val intent =
                                        Intent(this@RegisterActivity, HomeActivity::class.java)
                                    startActivity(intent)
                                } else {
                                    Toast.makeText(
                                        this@RegisterActivity,
                                        it.getString("errorMessage"),
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            } catch (e: JSONException) {
                                Toast.makeText(
                                    this@RegisterActivity,
                                    e.message,
                                    Toast.LENGTH_SHORT
                                ).show()

                            }
                        },
                        Response.ErrorListener
                        {
                            System.out.println(it)
                            Toast.makeText(
                                this@RegisterActivity,
                                "Could not reach the server !!",
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
                        this@RegisterActivity,
                        "Password mismatch",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                Toast.makeText(
                    this@RegisterActivity,
                    "All fields are mandatory",
                    Toast.LENGTH_SHORT
                ).show()
            }


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

    override fun onPause() {
        super.onPause()
        finish()
    }
}
