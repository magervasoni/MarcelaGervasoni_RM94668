package com.github.magervasoni.marcelagervasoni_rm94668

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.*
import androidx.room.Room
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ItemsViewModel(application: Application) : AndroidViewModel(application) {
    private val itemDao: ItemDao
    val itemsLiveData: LiveData<List<ItemModel>>

    private val _filteredItems = MutableLiveData<List<ItemModel>>()
    val filteredItems: LiveData<List<ItemModel>> get() = _filteredItems

    init {
        val database = Room.databaseBuilder(
            getApplication(),
            ItemDatabase::class.java,
            "items_database"
        ).build()

        itemDao = database.itemDao()
        itemsLiveData = itemDao.getAll()
    }


    fun searchItems(query: String): LiveData<List<ItemModel>> {
        val formattedQuery = "%${query.trim()}%"
        return itemDao.searchItems(formattedQuery)
    }
    fun addItem(titulo: String, descricao: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val newItem = ItemModel(titulo = titulo, descricao = descricao)
            itemDao.insert(newItem)
        }
    }
    fun removeItem(item: ItemModel) {
        viewModelScope.launch(Dispatchers.IO) {
            itemDao.delete(item)
        }
    }

}