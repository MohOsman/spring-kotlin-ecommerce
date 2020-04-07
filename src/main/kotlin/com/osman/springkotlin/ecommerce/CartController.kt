package com.osman.springkotlin.ecommerce

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController()
class CartController{

    @Autowired
    lateinit var cartService: CartService

    @PostMapping("/addToCart/{productId}/{quantity}")
    fun addToCart(@PathVariable productId: String, @PathVariable quantity: Int): ResponseEntity<ShoppingCart> {
        val shoppingCart = cartService.addTodCart(productId, quantity)
        return ResponseEntity(shoppingCart, HttpStatus.OK)
    }

    @GetMapping("/cart")
    fun viewCart(): ResponseEntity<ShoppingCart> {
        val shoppingCart = cartService.getShoppingCart()
        return ResponseEntity(shoppingCart, HttpStatus.OK)
    }

    @DeleteMapping("/removeActiveCart")
    fun removeCart(): ResponseEntity<String> {
        val shoppingCart = cartService.getShoppingCart()
        cartService.removeActiveCart(shoppingCart)
        return ResponseEntity("DELETED", HttpStatus.OK)
    }


}
