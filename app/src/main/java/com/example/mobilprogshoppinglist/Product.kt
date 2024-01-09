package com.example.mobilprogshoppinglist

class Product(
    var id: Int = 0,
    var name: String? = null,
    var quantity: Int = 0
){
    constructor(name: String?, quantity: Int) : this() {
        this.name = name
        this.quantity = quantity
    }
}

