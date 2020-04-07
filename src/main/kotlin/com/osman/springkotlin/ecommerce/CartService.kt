package com.osman.springkotlin.ecommerce

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Service
import java.util.*


@Service
class CartService @Autowired constructor(val productService: ProductService,
                                         val inventoryService: InventoryService,
                                         val cartRepository: CartRepository) {

    fun addTodCart(productId: String, quantity: Int): ShoppingCart {
        val inventoryQuantity = inventoryService.getProductQuantity(productId)
        val maxQuantity = if (inventoryQuantity!! <= quantity) inventoryQuantity else quantity

        if (doesProductExistAlreadyInCart(productId))
            updateProductQuantity(productId, maxQuantity)
        else
            addNewLineProduct(productId, maxQuantity)
        return getShoppingCart()
    }

    private fun addNewLineProduct(productId: String, quantity: Int) {
        val product: Product = productService.getProduct(productId)
        val lineProduct = LineProduct(UUID.randomUUID().toString(), product, quantity, quantity * product.unitPrice)
        if (cartRepository.count() > 0) {
            val shoppingCart = getShoppingCart()
            shoppingCart.lineProducts.add(lineProduct)
            val totalPrice = calcluateTotalPrice(shoppingCart)
            val updatedShoppingCart = ShoppingCart(shoppingCart.id, shoppingCart.lineProducts, totalPrice)
            cartRepository.delete(shoppingCart)
            cartRepository.save(updatedShoppingCart)
        } else {
            val shoppingCart = ShoppingCart(UUID.randomUUID().toString(),
                    mutableListOf(lineProduct),
                    lineProduct.price)
            cartRepository.save(shoppingCart)
        }


    }

    private fun updateProductQuantity(productId: String, quantity: Int) {
        val oldLineProduct = getLineProduct(productId)
        val shoppingCart = getShoppingCart()
        val lineProduct = LineProduct(oldLineProduct.id, oldLineProduct.product, quantity, quantity * oldLineProduct.product.unitPrice)
        shoppingCart.lineProducts.removeIf { line -> line.product.id == productId }
        shoppingCart.lineProducts.add(lineProduct)
        val totalprice = calcluateTotalPrice(shoppingCart)
        val updatedShoppingCart = ShoppingCart(getShoppingCart().id, shoppingCart.lineProducts, totalprice)

        cartRepository.delete(getShoppingCart())
        cartRepository.save(updatedShoppingCart)

    }

    private fun calcluateTotalPrice(shoppingCart: ShoppingCart): Int {
        return shoppingCart.lineProducts.sumBy { lineProduct -> lineProduct.price }
    }

    private fun doesProductExistAlreadyInCart(productId: String): Boolean {
        return if (cartRepository.count() > 0)
            cartRepository
                    .findAll()
                    .first()
                    .lineProducts
                    .any { lineProduct -> lineProduct.product.id == productId }
        else
            false

    }

    private fun getLineProduct(productId: String): LineProduct {
        return cartRepository.findAll()
                .first().lineProducts
                .find { lineProduct -> lineProduct.product.id == productId }!!
    }

    private fun save(shoppingCart: ShoppingCart): ShoppingCart {
        return if (cartRepository.count() == 1L) {
            val lineProducts = getShoppingCart().lineProducts
            lineProducts.addAll(shoppingCart.lineProducts)
            val updatedShoppingCart = ShoppingCart(getShoppingCart().id, lineProducts, shoppingCart.totalPrice)
            cartRepository.save(updatedShoppingCart)
        } else {
            cartRepository.save(shoppingCart)
        }

    }

    fun getShoppingCart(): ShoppingCart {
        if (cartRepository.count() == 0L) {
            throw NotFoundException(" Active Shopping cart does not exist")
        } else {
            return cartRepository.findAll().first()
        }
    }

    fun removeActiveCart(shoppingCart: ShoppingCart) {
        cartRepository.deleteById(getShoppingCart().id)
    }

}

interface CartRepository : MongoRepository<ShoppingCart, String>


