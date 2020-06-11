package com.moriarity_code.pedalplates.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.moriarity_code.pedalplates.R
import com.moriarity_code.pedalplates.activity.DetailActivity
import com.moriarity_code.pedalplates.model.Restaurants
import com.squareup.picasso.Picasso

class HomeAdapter(val context: Context, private val itemList: ArrayList<Restaurants>) :
    RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {
    class HomeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtResName: TextView = view.findViewById(R.id.txtRestaurantName)
        val txtCostPerPerson: TextView = view.findViewById(R.id.txtCostPerPerson)
        val imgRes: ImageView = view.findViewById(R.id.imgRestaurantImage)
        val txtRate: TextView = view.findViewById(R.id.txtRating)
        val llContent: LinearLayout = view.findViewById(R.id.llContent)
        val imgFavourite: ImageView = view.findViewById(R.id.imgFavourite)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_home_single_item, parent, false)
        return HomeViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val res = itemList[position]
        holder.txtResName.text = res.name
        holder.txtCostPerPerson.text = res.cost_for_one
        holder.txtRate.text = res.rating
        Picasso.get().load(res.img_url).error(R.drawable.ic_food).into(holder.imgRes)
        holder.imgFavourite.setOnClickListener {
            holder.imgFavourite.setImageResource(R.drawable.ic_favourite_full)
        }
        holder.llContent.setOnClickListener {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra("res_id", res.userId)
            context.startActivity(intent)
        }


    }
}