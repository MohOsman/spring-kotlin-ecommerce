package osman.springktolin.com.springbootkotlin.security

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController()
class CartController{

    @Autowired lateinit var cartService: CartService

    @GetMapping("/products")
    fun  getAll() = "test"

    @PostMapping("/addToCart/{productId}/{quantity}")
    fun  addToCart(@PathVariable productId : String , @PathVariable quantity : Int) : ResponseEntity<OrderProduct> {
        val orderProduct  = cartService.addToCart(productId, quantity)
        return ResponseEntity(orderProduct, HttpStatus.OK)
    }




}