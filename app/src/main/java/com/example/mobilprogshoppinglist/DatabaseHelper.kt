import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.mobilprogshoppinglist.Product

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onOpen(db: SQLiteDatabase) {
        super.onOpen(db)
        db.execSQL("DROP TABLE IF EXISTS products")
        db.execSQL(SQL_CREATE_PRODUCTS_TABLE)
        val testData = arrayOf(
            Product(name = "alma", quantity = 3),
            Product(name = "barack", quantity = 5),
            Product(name = "körte", quantity = 3),
            Product(name = "túró", quantity = 5)
        )

        for (product in testData) {
            val values = ContentValues().apply {
                put("name", product.name)
                put("quantity", product.quantity)
            }

            val newProductId = db.insert("products", null, values)
            product.id = newProductId.toInt()
        }
    }
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_PRODUCTS_TABLE)
        db.execSQL(SQL_CREATE_LISTS_TABLE)
    }


    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Upgrade logic
    }

    @SuppressLint("Range")
    fun getAllProducts(): List<Product> {
        val productList = mutableListOf<Product>()
        val db = readableDatabase

        val cursor = db.query(
            "products",
            arrayOf("id", "name", "quantity"),
            null, null, null, null, null
        )

        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndex("id"))
            val name = cursor.getString(cursor.getColumnIndex("name"))
            val quantity = cursor.getInt(cursor.getColumnIndex("quantity"))

            val product = Product()
            product.id = id
            product.name = name
            product.quantity = quantity

            productList.add(product)
        }

        cursor.close()
        db.close()

        return productList
    }


    companion object {
        const val DATABASE_NAME = "shopping_list.db"
        const val DATABASE_VERSION = 1

        private val SQL_CREATE_PRODUCTS_TABLE =
            "CREATE TABLE products (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, quantity INTEGER)"

        private val SQL_CREATE_LISTS_TABLE =
            "CREATE TABLE lists (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT)"
    }
}