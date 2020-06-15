package com.moriarity_code.pedalplates.adapter

import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.moriarity_code.pedalplates.R
import com.moriarity_code.pedalplates.activity.DetailActivity
import com.moriarity_code.pedalplates.database.FavouriteDatabase
import com.moriarity_code.pedalplates.database.FavouriteEntity
import com.squareup.picasso.Picasso

class FavouriteAdapter(
    val context: Context,
    private val itemList: List<FavouriteEntity>,
    val itemClick: (Boolean) -> Unit
) :
    RecyclerView.Adapter<FavouriteAdapter.FavouriteViewHolder>() {
    class FavouriteViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imgRes: ImageView = view.findViewById(R.id.imgRestaurantImage)
        val txtResName: TextView = view.findViewById(R.id.txtRestaurantName)
        val txtCost: TextView = view.findViewById(R.id.txtCostPerPerson)
        val cvFav: CardView = view.findViewById(R.id.cvFav)
        val btnRemove: TextView = view.findViewById(R.id.btnRemove)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_favourite_single_element, parent, false)
        return FavouriteViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: FavouriteViewHolder, position: Int) {
        val fav = itemList[position]

        holder.txtResName.text = fav.name
        holder.txtCost.text = "Rs. ${fav.cost_for_one}/person"
        Picasso.get().load(fav.image_url).error(R.drawable.ic_food).into(holder.imgRes)

        holder.cvFav.setOnClickListener {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra("res_id", fav.id)
            intent.putExtra("name", fav.name)
            context.startActivity(intent)
        }
        val restaurantEntity = FavouriteEntity(
            fav.id,
            fav.name,
            fav.rating,
            fav.cost_for_one,
            fav.image_url
        )
        holder.btnRemove.setOnClickListener {
            val async = FavouriteAdapter.DeleteFavourite(context, restaurantEntity).execute()
            val result = async.get()
            if (result) {
                Toast.makeText(
                    context,
                    "Removed from favourites",
                    Toast.LENGTH_SHORT
                ).show()
                itemClick(true)
            } else {
                Toast.makeText(
                    context,
                    "Some error occured",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    class DeleteFavourite(val context: Context, private val restaurantEntity: FavouriteEntity) :
        AsyncTask<Void, Void, Boolean>() {
        override fun doInBackground(vararg params: Void?): Boolean {
            val db = Room.databaseBuilder(context, FavouriteDatabase::class.java, "favourites_db")
                .build()
            db.FavouriteDao().deleteFav(restaurantEntity)
            db.close()
            return true
        }

    }
}