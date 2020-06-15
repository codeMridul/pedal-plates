package com.moriarity_code.pedalplates.adapter

import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.moriarity_code.pedalplates.R
import com.moriarity_code.pedalplates.activity.DetailActivity
import com.moriarity_code.pedalplates.database.FavouriteDatabase
import com.moriarity_code.pedalplates.database.FavouriteEntity
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
            .inflate(R.layout.recycler_home_single_element, parent, false)
        return HomeViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val res = itemList[position]
        holder.txtResName.text = res.name
        holder.txtCostPerPerson.text = "Rs. ${res.cost_for_one}/person"
        holder.txtRate.text = res.rating
        Picasso.get().load(res.img_url).error(R.drawable.ic_food).into(holder.imgRes)

        val restaurantEntity = FavouriteEntity(
            res.userId,
            res.name,
            res.rating,
            res.cost_for_one,
            res.img_url
        )
        val checkFav = DBAsyncTask(context, restaurantEntity, 1).execute()
        val isFav = checkFav.get()
        if (isFav) {
            holder.imgFavourite.setImageResource(R.drawable.ic_favourite_full)
        } else {
            holder.imgFavourite.setImageResource(R.drawable.ic_favourite_empty)
        }


        holder.imgFavourite.setOnClickListener {
            if (!DBAsyncTask(context, restaurantEntity, 1).execute().get()) {
                val async = DBAsyncTask(context, restaurantEntity, 2).execute()
                val result = async.get()
                if (result) {
                    Toast.makeText(
                        context,
                        "Added to favourites",
                        Toast.LENGTH_SHORT
                    ).show()
                    holder.imgFavourite.setImageResource(R.drawable.ic_favourite_full)
                } else {
                    Toast.makeText(
                        context,
                        "Some error occured",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                val async = DBAsyncTask(context, restaurantEntity, 3).execute()
                val result = async.get()
                if (result) {
                    Toast.makeText(
                        context,
                        "Removed from favourites",
                        Toast.LENGTH_SHORT
                    ).show()
                    holder.imgFavourite.setImageResource(R.drawable.ic_favourite_empty)
                } else {
                    Toast.makeText(
                        context,
                        "Some error occured",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
        holder.llContent.setOnClickListener {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra("res_id", res.userId)
            intent.putExtra("name", res.name)
            context.startActivity(intent)
        }
    }

    class DBAsyncTask(
        val context: Context,
        private val restaurantEntity: FavouriteEntity,
        private val mode: Int
    ) : AsyncTask<Void, Void, Boolean>() {
        /**
        Mode 1 -> Check DB if the Restaurant is  in favourite or not
        Mode 2 -> Save the favourite into DB as favourites
        Mode 3 -> Remove the favourites book
         **/
        private val db =
            Room.databaseBuilder(context, FavouriteDatabase::class.java, "favourites_db").build()

        override fun doInBackground(vararg params: Void?): Boolean {
            when (mode) {
                1 -> {
                    // Check DB if the Restaurant is  in favourite or not
                    val favourite: FavouriteEntity? =
                        db.FavouriteDao().getFavouriteById(restaurantEntity.id)
                    db.close()
                    return favourite != null
                }
                2 -> {
                    // Save the favourite into DB as favourites
                    db.FavouriteDao().insertFav(restaurantEntity)
                    db.close()
                    return true
                }
                3 -> {
                    // Remove the favourites book
                    db.FavouriteDao().deleteFav(restaurantEntity)
                    db.close()
                    return true
                }
            }
            return false
        }
    }
}