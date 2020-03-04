package com.osman.springkotlin.ecommerce

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ProductService @Autowired constructor(val productRepository: ProductRepository, val inventoryService: InventoryService) {


    // Todo add some validation to the logic
    fun create(product: Product, stockQuantiry: Int): Product {
        if (productRepository.findById(product.id!!).isPresent) {
            throw  IllegalArgumentException("person with the id  ${product.id} already exist")
        }
        return save(product, stockQuantiry)
    }

    private val inventory_Id = "inventory-1321"

    private fun save(product: Product, stockQuantity: Int): Product {
        return if (inventoryService.count() == 0L) {
            val producyQuantityMap = mutableMapOf(product.id!! to stockQuantity)
            val inventory = Invetory(inventory_Id, producyQuantityMap)
            inventoryService.createInventory(inventory)
            productRepository.save(product)
        } else {
            val productQuantityMap = inventoryService.getInventory(inventory_Id).InventoryHash.toMutableMap()
            productQuantityMap[product.id!!] = stockQuantity
            inventoryService.createInventory(Invetory(inventory_Id, productQuantityMap))
            productRepository.save(product)
        }

    }

    fun getProduct(id: String): Product = productRepository.findById(id).orElseThrow { NotFoundException("Product does not exist ") }

    fun deleteAll() = productRepository.deleteAll()

    fun getAll(): MutableList<Product> = productRepository.findAll()
    fun delete(id: String) {
        if (!productRepository.existsById(id)) {
            throw NotFoundException("product with id $id does not exist")
        }
        productRepository.deleteById(id)
    }


}