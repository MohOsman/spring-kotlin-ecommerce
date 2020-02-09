package com.osman.springkotlin.security

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController("")
class ProductController {
    @Autowired
    lateinit var productService: ProductService

    @PostMapping("/create")
    fun createItem(@RequestBody product: Product): ResponseEntity<Product> {
        val item = productService.create(product)
        return ResponseEntity(item, HttpStatus.CREATED)
    }

    @GetMapping("/item/{id}")
    fun getitem(@PathVariable id: String): ResponseEntity<Product> {
        try {
            return ResponseEntity(productService.getProduct(id), HttpStatus.OK)
        } catch (e: NotFoundException) { return ResponseEntity(HttpStatus.NOT_FOUND)

        }
    }

    //only authrozied
    @DeleteMapping("/deleteAll")
    fun  deleteAll() :Unit {
        productService.deleteAll();
    }

    @GetMapping("/items")
    fun getAll() : ResponseEntity<List<Product>>{
        return ResponseEntity(productService.getAll(), HttpStatus.OK)
    }



}