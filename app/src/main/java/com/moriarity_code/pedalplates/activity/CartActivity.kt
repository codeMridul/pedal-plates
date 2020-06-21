package com.moriarity_code.pedalplates.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.moriarity_code.pedalplates.R
import com.moriarity_code.pedalplates.adapter.CartAdapter
import kotlinx.android.synthetic.main.activity_cart.*
import kotlinx.android.synthetic.main.recycler_cart_single_element.view.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.Serializable

class CartActivity : AppCompatActivity(), Serializable {
    private lateinit var recyclerAdapter: CartAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var sharedPreferences: SharedPreferences


    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)
        setToolbar()
        sharedPreferences =
            getSharedPreferences(getString(R.string.preference_file_name), Context.MODE_PRIVATE)
        val userName = sharedPreferences.getString("name", "admin")
        val userId = sharedPreferences.getString("user_id", "0000")
        val mobileNum = sharedPreferences.getString("mobile_number", "0000000000")
        val email = sharedPreferences.getString("emailId", null)
        val address = sharedPreferences.getString("address", null)

        val resName = intent.getStringExtra("name")
        val resId = intent.getStringExtra("res_id")
        val cartName = intent.getStringArrayListExtra("cartName")
        val cartCost = intent.getStringArrayListExtra("cartCost")
        val cartId = intent.getStringArrayListExtra("cartId")
        txtRestaurantName.text = resName
        val billNo = (10000 * Math.random()).toInt()
        txtBillNo.text = "Bill No: $billNo"
        cartBillHeader.txtProductName.text = getString(R.string.item)
        cartBillHeader.txtProductCost.text = getString(R.string.price)
        cartTotalBill.txtProductName.text = getString(R.string.total_bill)

        recyclerView = findViewById(R.id.recyclerCart)
        layoutManager = LinearLayoutManager(this@CartActivity)

        recyclerAdapter = CartAdapter(this@CartActivity, cartName!!, cartCost!!)
        recyclerView.adapter = recyclerAdapter
        recyclerView.layoutManager = layoutManager

        var total = 150
        for (i in cartCost) {
            total += Integer.parseInt(i)
        }
        cartTotalBill.txtProductCost.text = "Rs. $total"

        btnOrder.setOnClickListener {

            val queue = Volley.newRequestQueue(this@CartActivity)
            val url = "http://13.235.250.119/v2/place_order/fetch_result/"
            val jsonParams = JSONObject()
            jsonParams.put("user_id", userId)
            jsonParams.put("restaurant_id", resId)
            jsonParams.put("total_cost", total.toString())
            val jsonArray = JSONArray()
            for (id in cartId!!) {
                val jsonOb = JSONObject()
                jsonOb.put("food_item_id", id)
                jsonArray.put(jsonOb)
            }
            jsonParams.put("food", jsonArray)

            val jsonObjectRequest = object : JsonObjectRequest(
                Request.Method.POST,
                url,
                jsonParams,
                Response.Listener { response ->
                    try {
                        val it = response.getJSONObject("data")
                        val success = it.getBoolean("success")
                        if (success) {
                            Toast.makeText(
                                this@CartActivity,
                                "Order Placed",
                                Toast.LENGTH_SHORT
                            ).show()
                            val intent = Intent(this@CartActivity, OrderSuccessActivity::class.java)
                            intent.putExtra("total", total.toString())
                            startActivity(intent)
                            this@CartActivity.finish()
                        } else {
                            Toast.makeText(
                                this@CartActivity,
                                "Order could not be placed",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } catch (e: JSONException) {
                        Toast.makeText(
                            this@CartActivity,
                            e.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                },
                Response.ErrorListener {
                    Toast.makeText(
                        this@CartActivity,
                        it.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            ) {
                override fun getHeaders(): MutableMap<String, String> {
                    val header = HashMap<String, String>()
                    header["Content-Type"] = "application/json"
                    header["token"] = "ff94a4233d0da6"
                    return header
                }
            }
            queue.add(jsonObjectRequest)
        }
    }
    private fun setToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Cart"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
    }
}