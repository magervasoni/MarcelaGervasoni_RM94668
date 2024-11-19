package com.github.magervasoni.marcelagervasoni_rm94668

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: ItemsViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Lista Dicas"

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val itemsAdapter = ItemsAdapter { item ->
            viewModel.removeItem(item)
        }
        recyclerView.adapter = itemsAdapter

        val tituloEditText = findViewById<EditText>(R.id.tituloEditText)
        val descricaoEditText = findViewById<EditText>(R.id.descricaoEditText)

        val button = findViewById<Button>(R.id.button)
        button.setOnClickListener {
            if (tituloEditText.text.isEmpty()) {
                tituloEditText.error = "Título Dica"
                return@setOnClickListener
            }
            if (descricaoEditText.text.isEmpty()) {
                descricaoEditText.error = "Descrição Dica"
                return@setOnClickListener
            }
            val titulo = tituloEditText.text.toString().trim()
            val descricao = descricaoEditText.text.toString().trim()

            viewModel.addItem(titulo, descricao)

            viewModel.itemsLiveData.observe(this) { items ->
                itemsAdapter.updateItems(items)
            }
            tituloEditText.text.clear()
            descricaoEditText.text.clear()
        }
        val searchView = findViewById<SearchView>(R.id.searchView)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    if (it.isEmpty()) {
                        viewModel.itemsLiveData.observe(this@MainActivity) { items ->
                            itemsAdapter.updateItems(items)
                        }
                    } else {
                        viewModel.searchItems(it).observe(this@MainActivity) { filteredItems ->
                            itemsAdapter.updateItems(filteredItems)
                        }
                    }
                }
                return true
            }
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }
        })

        val viewModelFactory = ItemsViewModelFactory(application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(ItemsViewModel::class.java)

        viewModel.itemsLiveData.observe(this) { items ->
            itemsAdapter.updateItems(items)
        }

        viewModel.itemsLiveData.observe(this) { items ->
            itemsAdapter.updateItems(items)
        }
    }
}