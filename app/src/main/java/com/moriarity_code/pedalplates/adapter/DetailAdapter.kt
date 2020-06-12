package com.moriarity_code.pedalplates.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat.getColor
import androidx.recyclerview.widget.RecyclerView
import com.moriarity_code.pedalplates.R.*
import com.moriarity_code.pedalplates.model.RestaurantMenu

class DetailAdapter(val context: Context, private val itemList: ArrayList<RestaurantMenu>) :
    RecyclerView.Adapter<DetailAdapter.DetailViewHolder>() {
    class DetailViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtProductName: TextView = view.findViewById(id.txtProductName)
        val txtProductCost: TextView = view.findViewById(id.txtProductCost)
        val cvDetail: CardView = view.findViewById(id.cvDetail)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(layout.recycler_detail_single_element, parent, false)
        return DetailViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: DetailViewHolder, position: Int) {
        val product = itemList[position]
        holder.txtProductName.text = product.name
        holder.txtProductCost.text = "Rs. ${product.cost_for_one}"

        holder.cvDetail.setOnClickListener {
            holder.cvDetail.setCardBackgroundColor(getColor(context, color.on_button_pressed))
        }
    }

}