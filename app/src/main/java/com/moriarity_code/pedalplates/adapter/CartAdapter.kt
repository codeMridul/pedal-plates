package com.moriarity_code.pedalplates.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.moriarity_code.pedalplates.R
import com.moriarity_code.pedalplates.model.RestaurantMenu

class CartAdapter(val context: Context, private val cartList: ArrayList<RestaurantMenu>) :
    RecyclerView.Adapter<CartAdapter.CartViewHolder>() {
    var totalSum = 0

    class CartViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtProductName: TextView = view.findViewById(R.id.txtProductName)
        val txtProductCost: TextView = view.findViewById(R.id.txtProductCost)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_cart_single_element, parent, false)
        return CartViewHolder(view)
    }

    override fun getItemCount(): Int {
        return cartList.size
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val product = cartList[position]
        holder.txtProductName.text = product.name
        holder.txtProductCost.text = "Rs. ${product.cost_for_one}"
        totalSum += Integer.parseInt(product.cost_for_one)
        println(totalSum)
    }
}