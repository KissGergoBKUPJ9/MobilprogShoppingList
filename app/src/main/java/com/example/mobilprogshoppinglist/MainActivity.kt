package com.example.mobilprogshoppinglist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mobilprogshoppinglist.ui.theme.MobilprogShoppingListTheme

class MainActivity : ComponentActivity() {
    private lateinit var shoppingListView: ShoppingListView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        shoppingListView = ShoppingListView(this)
        shoppingListView.loadProductsFromDatabase()
        setContent {
            MobilprogShoppingListTheme {
                    ShoppingListContent(shoppingListView)
            }
            FloatingActionButton(
                onClick = {  },
                modifier = Modifier
                    .absolutePadding(right = 5.dp)
            ) {
                Text("+")
            }
        }
    }
}

@Composable
fun ShoppingListContent(shoppingListView: ShoppingListView) {
    Column {
        shoppingListView.apply {
            LazyColumn {
                items(products) { product ->
                    ProductListItem(product = product)
                }
            }
        }
    }
}

@Composable
fun ProductListItem(product: Product) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "${product.name}",
                style = MaterialTheme.typography.displayMedium
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Mennyiség: ${product.quantity}",
                style = MaterialTheme.typography.displaySmall
            )
        }
    }
}