package com.osman.springkotlin.ecommerce

import java.util.*

val testProductId = UUID.randomUUID().toString()
val testProductId2 = UUID.randomUUID().toString()
val inventoryId = UUID.randomUUID().toString()
val shoppingCartID = UUID.randomUUID().toString()
val lineProductID = UUID.randomUUID().toString()

fun getTestProduct(): Optional<Product> = Optional.of(Product(testProductId, "Adidas JBL 2X10", "Latest Adidas", 450))
fun getTestProduct2(): Optional<Product> = Optional.of(Product(testProductId2, "Adidas JBL 2X12", " Most Latest Adidas", 550))
fun getInventory(): Invetory {
    return Invetory(inventoryId, hashMapOf(testProductId to 200, testProductId2 to 15))

}

fun createLinProducts(quantity: Int = 1): List<LineProduct> {
    return mutableListOf(LineProduct(lineProductID, getTestProduct().get(), quantity))
}

fun getTestShoppingCart(quantity: Int = 1, product: Product): ShoppingCart {
    return ShoppingCart(shoppingCartID, createLinProducts(quantity), product.unitPrice * quantity)
}