import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.mobilprogshoppinglist.Product
import com.example.mobilprogshoppinglist.ShoppingListView
import com.example.mobilprogshoppinglist.ui.theme.MobilprogShoppingListTheme

class MainActivity : ComponentActivity() {
    private lateinit var shoppingListView: ShoppingListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        shoppingListView = ShoppingListView(this)
        shoppingListView.loadProductsFromDatabase()
        setContent {
            MobilprogShoppingListTheme {
                MainContent(shoppingListView)
            }
        }
    }

    @Composable
    private fun MainContent(shoppingListView: ShoppingListView) {
        var isDialogVisible by remember { mutableStateOf(false) }
        var newProduct by remember { mutableStateOf(Product("", 0)) }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            ShoppingListContent(shoppingListView)

            FloatingActionButton(
                onClick = {
                    isDialogVisible = true
                },
                modifier = Modifier
                    .absolutePadding(right = 16.dp, bottom = 16.dp)
            ) {
                Text("+")
            }

            if (isDialogVisible) {
                // Composable for entering new product details
                NewProductDialog(
                    onDismiss = {
                        isDialogVisible = false
                    },
                    onProductAdded = { product ->
                        shoppingListView.addProduct(product)
                        isDialogVisible = false
                    }
                )
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun NewProductDialog(
        onDismiss: () -> Unit,
        onProductAdded: (Product) -> Unit
    ) {
        var productName by remember { mutableStateOf("") }
        var productQuantity by remember { mutableStateOf(0) }

        AlertDialog(
            onDismissRequest = onDismiss,
            title = { Text("Add New Product") },
            text = {
                Column {
                    TextField(
                        value = productName,
                        onValueChange = { productName = it },
                        label = { Text("Product Name") }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    TextField(
                        value = productQuantity.toString(),
                        onValueChange = {
                            productQuantity = it.toIntOrNull() ?: 0
                        },
                        label = { Text("Quantity") },
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        val newProduct = Product(productName, productQuantity)
                        onProductAdded(newProduct)
                    }
                ) {
                    Text("Add Product")
                }
            },
            dismissButton = {
                Button(onClick = onDismiss) {
                    Text("Cancel")
                }
            }
        )
    }

    @Composable
    private fun ShoppingListContent(shoppingListView: ShoppingListView) {
        Column {
            shoppingListView.products.forEach { product ->
                ProductListItem(product = product)
            }
        }
    }

    @Composable
    private fun ProductListItem(product: Product) {
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
                    text = "Quantity: ${product.quantity}",
                    style = MaterialTheme.typography.displaySmall
                )
            }
        }
    }
}
