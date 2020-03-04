package com.osman.springkotlin.ecommerce

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*


@RestController()
class ProductController {
    @Autowired
    lateinit var productService: ProductService

    @PostMapping("/add/{stockQuantity}")
    fun createProduct(@RequestBody product: Product, @PathVariable stockQuantity: Int): ResponseEntity<Product> {
        val uuid = UUID.randomUUID().toString()
        product.id = uuid
        val item = productService.create(product, stockQuantity)


        return ResponseEntity(item, HttpStatus.CREATED)
    }

    @GetMapping("/product/{id}")
    fun getProduct(@PathVariable id: String): ResponseEntity<Product> {
        try {
            return ResponseEntity(productService.getProduct(id), HttpStatus.OK)
        } catch (e: NotFoundException) {
            return ResponseEntity(HttpStatus.NOT_FOUND)

        }
    }

    //only authrozied
    @DeleteMapping("/deleteAll")
    fun deleteAll() {
        productService.deleteAll()
    }

    @DeleteMapping("/delete/{id}")
    fun deleteProduct(@PathVariable id: String): ResponseEntity<Product> {
        return try {
            productService.delete(id)
            ResponseEntity(HttpStatus.OK)
        } catch (e: NotFoundException) {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }


    @GetMapping("/products")
    fun getAll(): ResponseEntity<List<Product>> {
        return ResponseEntity(productService.getAll(), HttpStatus.OK)
    }


}