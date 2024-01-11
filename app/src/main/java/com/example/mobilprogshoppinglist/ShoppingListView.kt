package com.example.mobilprogshoppinglist

import DatabaseHelper
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ShoppingListView(context: Context) : RecyclerView.Adapter<ShoppingListView.ViewHolder>() {

    var products: List<Product> = listOf()
    private val databaseHelper = DatabaseHelper(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.shopping_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = products[position]

        holder.productNameTextView.text = product.name
        holder.productQuantityTextView.text = product.quantity.toString()
    }

    override fun getItemCount(): Int {
        return products.size
    }

    private fun updateProducts(products: List<Product>) {
        this.products = products
        notifyDataSetChanged()
    }
    fun loadProductsFromDatabase() {
        val productList = databaseHelper.getAllProducts()
        updateProducts(productList)
    }

    fun addProduct(product: Product) {
        val newProductId = databaseHelper.insertProduct(product)
        product.id = newProductId.toInt()
        val updatedList = products.toMutableList().apply { add(product) }
        updateProducts(updatedList)
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val productNameTextView: TextView = itemView.findViewById(R.id.productNameTextView)
        val productQuantityTextView: TextView = itemView.findViewById(R.id.productQuantityTextView)
    }
}