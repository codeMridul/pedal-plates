package com.moriarity_code.pedalplates.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.moriarity_code.pedalplates.R
import com.moriarity_code.pedalplates.model.OrderHistoryChild

class OrderHistoryChildAdapter(
    val context: Context,
    private val itemList: ArrayList<OrderHistoryChild>
) :
    RecyclerView.Adapter<OrderHistoryChildAdapter.OrderHistoryChildViewHolder>() {
    class OrderHistoryChildViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val txtProductName: TextView = view.findViewById(R.id.txtChildProductName)
        val txtProductCost: TextView = view.findViewById(R.id.txtChildProductCost)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderHistoryChildViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_history_child_single_element, parent, false)
        return OrderHistoryChildViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: OrderHistoryChildViewHolder, position: Int) {
        val product = itemList[position]
        holder.txtProductName.text = product.name
        holder.txtProductCost.text = "Rs. ${product.cost}"
    }

}