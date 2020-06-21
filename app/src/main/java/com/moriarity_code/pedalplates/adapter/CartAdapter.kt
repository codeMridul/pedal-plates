package com.moriarity_code.pedalplates.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.moriarity_code.pedalplates.R

class CartAdapter(
    val context: Context,
    private val cartName: java.util.ArrayList<String>,
    private val cartCost: java.util.ArrayList<String>
) :
    RecyclerView.Adapter<CartAdapter.CartViewHolder>() {
    private var totalSum = 0

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
        return cartName.size
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.txtProductName.text = "${position + 1}. ${cartName[position]}"
        holder.txtProductCost.text = "Rs. ${cartCost[position]}"
    }
}