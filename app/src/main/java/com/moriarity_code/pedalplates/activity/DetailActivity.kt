package com.moriarity_code.pedalplates.activity

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.moriarity_code.pedalplates.R
import com.moriarity_code.pedalplates.adapter.DetailAdapter
import com.moriarity_code.pedalplates.model.RestaurantMenu
import com.moriarity_code.pedalplates.util.ConnectionManager
import kotlinx.android.synthetic.main.activity_register.*
import org.json.JSONException

class DetailActivity : AppCompatActivity() {
    lateinit var recyclerView: RecyclerView
    lateinit var recyclerAdapter: DetailAdapter
    lateinit var layoutManager: GridLayoutManager
    private lateinit var resName: TextView

    val menuList = arrayListOf<RestaurantMenu>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        resName = findViewById(R.id.txtResName)
        setToolbar()
        resName.text = intent.getStringExtra("name")
        val id = intent.getStringExtra("res_id")

        recyclerView = findViewById(R.id.recyclerDetail)
        layoutManager = GridLayoutManager(this@DetailActivity, 3)

        val queue = Volley.newRequestQueue(this@DetailActivity)
        val url = "http://13.235.250.119/v2/restaurants/fetch_result/$id"

        if (ConnectionManager().checkConnectivity(this@DetailActivity)) {
            val jsonObjectRequest = object : JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                Response.Listener
                { response ->
                    try {
                        val it = response.getJSONObject("data")
                        val success = it.getBoolean("success")
                        val data = it.getJSONArray("data")
                        for (i in 0..data.length()) {
                            val resMenuObject = data.getJSONObject(i)
                            val menuObject = RestaurantMenu(
                                resMenuObject.getString("id"),
                                resMenuObject.getString("name"),
                                resMenuObject.getString("cost_for_one"),
                                resMenuObject.getString("restaurant_id")
                            )
                            menuList.add(menuObject)
                            recyclerAdapter = DetailAdapter(this@DetailActivity, menuList)
                            recyclerView.adapter = recyclerAdapter
                            recyclerView.layoutManager = layoutManager
                        }
                    } catch (e: JSONException) {
                        Toast.makeText(
                            this@DetailActivity,
                            e.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                },
                Response.ErrorListener {
                    Toast.makeText(
                        this@DetailActivity,
                        it.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            ) {
                override fun getHeaders(): MutableMap<String, String> {
                    val header = HashMap<String, String>()
                    header["Content-Type"] = "application/json"
                    header["token"] = "9bf534118365f1"
                    return header
                }
            }
            queue.add(jsonObjectRequest)
        } else {
            val dialog = AlertDialog.Builder(this@DetailActivity)
            dialog.setTitle("Error")
            dialog.setMessage("Internet Connection is not Found")
            dialog.setPositiveButton("Open Settings") { _, _ ->
                val settingsIntent = Intent(Settings.ACTION_WIRELESS_SETTINGS)
                startActivity(settingsIntent)
                this@DetailActivity.finish()
            }

            dialog.setNegativeButton("Exit") { _, _ ->
                ActivityCompat.finishAffinity(this@DetailActivity)
            }
            dialog.create()
            dialog.show()
        }
    }

    override fun onSupportNavigateUp(): Boolean {//this add functionality to the back arrow in the Action Bar
        val intent = Intent(this@DetailActivity, HomeActivity::class.java)
        startActivity(intent)
        finish()
        return true
    }

    private fun setToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Menu"
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}