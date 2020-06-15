package com.moriarity_code.pedalplates.fragment

import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.moriarity_code.pedalplates.R
import com.moriarity_code.pedalplates.adapter.FavouriteAdapter
import com.moriarity_code.pedalplates.database.FavouriteDatabase
import com.moriarity_code.pedalplates.database.FavouriteEntity

class FavouriteRestaurantFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var recyclerAdapter: FavouriteAdapter

    private var dbFavouriteList = listOf<FavouriteEntity>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_favourite_restaurant, container, false)
        recyclerView = view.findViewById(R.id.recyclerFavourite)

        layoutManager = LinearLayoutManager(activity as Context)
        dbFavouriteList = RetrieveFavourites(activity as Context).execute().get()

        if (activity != null) {
            recyclerAdapter = FavouriteAdapter(activity as Context, dbFavouriteList) {
                if (it) {
                    recyclerAdapter.notifyDataSetChanged()
                }
            }
            recyclerView.adapter = recyclerAdapter
            recyclerView.layoutManager = layoutManager
        }

        return view
    }

    class RetrieveFavourites(val context: Context) :
        AsyncTask<Void, Void, List<FavouriteEntity>>() {
        override fun doInBackground(vararg params: Void?): List<FavouriteEntity> {
            val db = Room.databaseBuilder(context, FavouriteDatabase::class.java, "favourites_db")
                .build()
            return db.FavouriteDao().getAllFavourites()
        }
    }
}