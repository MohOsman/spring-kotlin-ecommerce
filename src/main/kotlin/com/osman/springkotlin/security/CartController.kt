package com.osman.springkotlin.security

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController()
class CartController{

    @Autowired lateinit var cartService: CartService



    @PostMapping("/addToCart/{productId}/{quantity}")
    fun addToCart(@PathVariable productId: String, @PathVariable quantity: Int): ResponseEntity<CartProduct> {
        val orderProduct  = cartService.addToCart(productId, quantity)
        return ResponseEntity(orderProduct, HttpStatus.OK)
    }




}