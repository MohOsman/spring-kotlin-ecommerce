package com.osman.springkotlin.security

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import org.springframework.stereotype.Service
import java.util.*

@Service
class CartService @Autowired constructor(val cartRepository: CartRepository, val productService: ProductService) {


    fun getProduct(id: String): CartProduct = cartRepository.findById(id).orElseThrow { NotFoundException("Product with id $id   do not exist in the cart") }

    fun getProducts(): List<CartProduct> = cartRepository.findAll()

    fun getCartProductByProductId(productId: String): CartProduct = cartRepository.findByProductId(productId).orElseThrow { NotFoundException("Product with id $productId   do not exsit in the cart") }


    fun addToCart(productID: String, quantity: Int = 1): CartProduct {
        val product = productService.getProduct(productID)
        val maxQuantity = if (product.totalQuantity <= quantity) product.totalQuantity else quantity
        return if (cartRepository.findByProductId(productID).isPresent) {
            val cartProduct = getCartProductByProductId(productID)
            updateQuantity(maxQuantity, cartProduct)
        } else {
            createCartproduct(productID, maxQuantity)
        }

    }

    private fun createCartproduct(productID: String, quantity: Int): CartProduct {
        val product = productService.getProduct(productID)
        val totalPrice = product.unitPrice.times(quantity)
        val orderProduct = CartProduct(UUID.randomUUID().toString(), product, totalPrice, quantity)
        return cartRepository.save(orderProduct)
    }

    private fun updateQuantity(quantity: Int, cartProduct: CartProduct): CartProduct {
        val price = cartProduct.price.plus(cartProduct.Product.unitPrice * quantity)
        val updatedCartProduct = CartProduct(cartProduct.id, cartProduct.Product, price, quantity)
        return cartRepository.save(updatedCartProduct)
    }
}


@Repository
interface CartRepository : MongoRepository<CartProduct, String> {
    fun findByProductId(productId: String): Optional<CartProduct>

}
