package com.moriarity_code.pedalplates.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FavouriteDao {
    @Insert
    fun insertFav(favouriteEntity: FavouriteEntity)

    @Delete
    fun deleteFav(favouriteEntity: FavouriteEntity)

    @Query("SELECT * FROM favourites_db")
    fun getAllFavourites(): List<FavouriteEntity>

    @Query("SELECT * FROM favourites_db WHERE id = :id")
    fun getFavouriteById(id: String): FavouriteEntity
}