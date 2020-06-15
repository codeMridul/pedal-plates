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

class DetailAdapter(
    val context: Context,
    private val itemList: ArrayList<RestaurantMenu>,
    private val cartItem: ArrayList<RestaurantMenu>,
    val itemClick: (Boolean) -> Unit
) :
    RecyclerView.Adapter<DetailAdapter.DetailViewHolder>() {
    class DetailViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtProductName: TextView = view.findViewById(id.txtProductName)
        val txtProductCost: TextView = view.findViewById(id.txtProductCost)
        val cvDetail: CardView = view.findViewById(id.cvDetail)
        val txtAddToCart: TextView = view.findViewById(id.txtAddToCart)
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


        holder.txtAddToCart.setOnClickListener {
            if (cartItem.contains(product)) {
                cartItem.remove(product)
                holder.txtAddToCart.text = context.getString(string.add_to_cart)
                holder.cvDetail.setCardBackgroundColor(
                    getColor(
                        context,
                        color.on_button_not_pressed
                    )
                )
            } else {
                cartItem.add(product)
                holder.txtAddToCart.text = context.getString(string.remove)
                holder.cvDetail.setCardBackgroundColor(getColor(context, color.on_button_pressed))
            }
            itemClick(true)
        }
    }
}