package com.moriarity_code.pedalplates.activity

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.RelativeLayout
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
import java.io.Serializable

class DetailActivity : AppCompatActivity(), Serializable {
    lateinit var recyclerView: RecyclerView
    lateinit var recyclerAdapter: DetailAdapter
    lateinit var layoutManager: GridLayoutManager
    private lateinit var resName: TextView
    private lateinit var cartLayout: RelativeLayout
    private lateinit var txtCartItemNumber: TextView

    val menuList = arrayListOf<RestaurantMenu>()
    private val cartFoodName = arrayListOf<String>()
    private val cartFoodCost = arrayListOf<String>()
    private val cartFoodId = arrayListOf<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        resName = findViewById(R.id.txtResName)
        setToolbar()

        txtCartItemNumber = findViewById(R.id.txtCartItemNumber)
        cartLayout = findViewById(R.id.cartLayout)
        cartLayout.visibility = View.GONE
        val name = intent.getStringExtra("name")
        resName.text = name
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
                        if (success) {
                            val data = it.getJSONArray("data")
                            for (i in 0 until data.length()) {
                                val resMenuObject = data.getJSONObject(i)
                                val menuObject = RestaurantMenu(
                                    resMenuObject.getString("id"),
                                    resMenuObject.getString("name"),
                                    resMenuObject.getString("cost_for_one"),
                                    resMenuObject.getString("restaurant_id")
                                )
                                menuList.add(menuObject)
                                recyclerAdapter =
                                    DetailAdapter(
                                        this@DetailActivity,
                                        menuList,
                                        cartFoodName,
                                        cartFoodCost,
                                        cartFoodId
                                    ) {
                                        if (cartFoodName.isEmpty() && cartFoodCost.isEmpty())
                                            cartLayout.visibility = View.GONE
                                        else
                                            cartLayout.visibility = View.VISIBLE
                                        if (it) {
                                            txtCartItemNumber.text = cartFoodName.size.toString()
                                        }
                                    }
                                recyclerView.adapter = recyclerAdapter
                                recyclerView.layoutManager = layoutManager
                            }
                        } else {
                            Toast.makeText(
                                this@DetailActivity,
                                it.getString("successMessage"),
                                Toast.LENGTH_SHORT
                            ).show()
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

        cartLayout.setOnClickListener {
            val intent = Intent(this@DetailActivity, CartActivity::class.java)
            intent.putExtra("name", name)
            intent.putExtra("res_id", id)
            intent.putExtra("cartName", cartFoodName)
            intent.putExtra("cartCost", cartFoodCost)
            intent.putExtra("cartId", cartFoodId)
            startActivity(intent)
        }
    }

    override fun onSupportNavigateUp(): Boolean {//this add functionality to the back arrow in the Action Bar
        if (cartFoodName.isNotEmpty() && cartFoodCost.isNotEmpty()) {
            Toast.makeText(
                this@DetailActivity,
                "Empty your Cart before leaving the Restaurant",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            val intent = Intent(this@DetailActivity, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }
        return true
    }

    private fun setToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Menu"
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onBackPressed() {
        if (cartFoodName.isNotEmpty() && cartFoodCost.isNotEmpty()) {
            Toast.makeText(
                this@DetailActivity,
                "Empty your Cart before leaving the Restaurant",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            super.onBackPressed()
            finish()
        }
    }
}