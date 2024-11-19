package com.github.magervasoni.marcelagervasoni_rm94668

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ItemDao {

    @Insert
    fun insert(item: ItemModel)

    @Query("SELECT * FROM ItemModel")
    fun getAll(): LiveData<List<ItemModel>>

    @Query("SELECT * FROM ItemModel WHERE titulo LIKE :query OR descricao LIKE :query")
    fun searchItems(query: String): LiveData<List<ItemModel>>

    @Delete
    fun delete(item: ItemModel)
}