package com.example.mobilprogshoppinglist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.mobilprogshoppinglist.ui.theme.MobilprogShoppingListTheme

class MainActivity : ComponentActivity() {
    private lateinit var shoppingListView: ShoppingListView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        shoppingListView = ShoppingListView(this)
        shoppingListView.loadProductsFromDatabase()
        setContent {
            MobilprogShoppingListTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    ShoppingListContent(shoppingListView)
                }
            }
        }
    }
}

@Composable
fun ShoppingListContent(shoppingListView: ShoppingListView) {
    Column {
        // Egyéb felületelemek elhelyezése itt
        shoppingListView.apply {
            // A ShoppingListView megjelenítése
            LazyColumn {
                items(products) { product ->
                    Text(text = "Name: ${product.name}, Quantity: ${product.quantity}")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MobilprogShoppingListTheme {
        // Teszteléshez
        val shoppingListView = ShoppingListView(context = androidx.activity.ComponentActivity())
        ShoppingListContent(shoppingListView)
    }
}