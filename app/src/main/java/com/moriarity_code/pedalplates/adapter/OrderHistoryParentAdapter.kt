package com.moriarity_code.pedalplates.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.moriarity_code.pedalplates.R
import com.moriarity_code.pedalplates.model.OrderHistoryParent

class OrderHistoryParentAdapter(
    val context: Context,
    private val itemList: ArrayList<OrderHistoryParent>
) :
    RecyclerView.Adapter<OrderHistoryParentAdapter.OrderHistoryParentViewHolder>() {
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var recyclerAdapter: OrderHistoryChildAdapter

    class OrderHistoryParentViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val txtResName: TextView = view.findViewById(R.id.txtResName)
        val txtOrderDate: TextView = view.findViewById(R.id.txtOrderDate)
        val txtBill: TextView = view.findViewById(R.id.txtBill)
        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerChild)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): OrderHistoryParentViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_history_parent_single_element, parent, false)
        return OrderHistoryParentViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: OrderHistoryParentViewHolder, position: Int) {
        val order = itemList[position]
        holder.txtResName.text = order.restaurant_name
        holder.txtOrderDate.text = "Date: ${order.order_placed_at}"
        holder.txtBill.text = "Rs. ${order.total_cost}"

        val foodItem = order.food_items

        layoutManager = LinearLayoutManager(context)
        recyclerAdapter = OrderHistoryChildAdapter(context, foodItem)
        holder.recyclerView.adapter = recyclerAdapter
        holder.recyclerView.layoutManager = layoutManager
    }
}