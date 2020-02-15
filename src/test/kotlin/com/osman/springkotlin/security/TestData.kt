package com.osman.springkotlin.security

import java.util.*

val id = UUID.randomUUID().toString()
val cartProductId = UUID.randomUUID().toString()

fun getTestProduct(totalQuantity: Int = 200): Optional<Product> = Optional.of(Product(id, "Adidas JBL 2X10", "Latest Adidas", 450, totalQuantity))
fun getTestProduct2(): Optional<Product> = Optional.of(Product(cartProductId, "Adidas JBL 2X12", " Most Latest Adidas", 550, 100))
fun getTestCartProduct(quantity: Int = 1): Optional<CartProduct> = Optional.of(CartProduct(
        UUID.randomUUID().toString(),
        getTestProduct().get(),
        getTestProduct().get().unitPrice * quantity
        ,
        quantity))