package com.moriarity_code.pedalplates.activity

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.moriarity_code.pedalplates.R
import com.moriarity_code.pedalplates.adapter.CartAdapter
import com.moriarity_code.pedalplates.model.RestaurantMenu
import kotlinx.android.synthetic.main.activity_cart.*
import kotlinx.android.synthetic.main.recycler_cart_single_element.view.*
import java.io.Serializable

class CartActivity : AppCompatActivity(), Serializable {
    private lateinit var recyclerAdapter: CartAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)
        setToolbar()

        sharedPreferences =
            getSharedPreferences(getString(R.string.preference_file_name), Context.MODE_PRIVATE)
        val userName = sharedPreferences.getString("name", "admin")
        val userId = sharedPreferences.getString("user_id", "0000")

        val resName = intent.getStringExtra("name")
        val resId = intent.getStringExtra("res_id")
        val cartList = intent.getSerializableExtra("cart")
        txtRestaurantName.text = resName
        cartBillHeader.txtProductName.text = getString(R.string.item)
        cartBillHeader.txtProductCost.text = getString(R.string.price)

        recyclerView = findViewById(R.id.recyclerCart)
        layoutManager = LinearLayoutManager(this@CartActivity)

        recyclerAdapter = CartAdapter(this@CartActivity, cartList as ArrayList<RestaurantMenu>)
        recyclerView.adapter = recyclerAdapter
        recyclerView.layoutManager = layoutManager

    }

    private fun setToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Cart"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
    }
}