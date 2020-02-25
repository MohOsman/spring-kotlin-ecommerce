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
        val maxQuantity = if (inventoryQuantity!! >= quantity) inventoryQuantity else quantity

        val lineProduct = if (doesProductExistAlreadyInCart(productId))
            updateProductQuantity(productId, maxQuantity)
        else
            addNewLineProduct(productId, maxQuantity)

        val shoppingCart = ShoppingCart(UUID.randomUUID().toString(),
                mutableListOf(lineProduct),
                lineProduct.quantity * lineProduct.product.unitPrice)
        return cartRepository.save(shoppingCart)
    }

    private fun addNewLineProduct(productId: String, quantity: Int): LineProduct {
        val product: Product = productService.getProduct(productId)
        return LineProduct(UUID.randomUUID().toString(), product, quantity)
    }

    private fun updateProductQuantity(productId: String, quantity: Int): LineProduct {
        val oldLineProduct = getLineProduct(productId)
        return LineProduct(oldLineProduct.id, oldLineProduct.product, quantity)


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


}

interface CartRepository : MongoRepository<ShoppingCart, String>


