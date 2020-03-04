package com.osman.springkotlin.ecommerce

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class InventoryController {

    @Autowired
    lateinit var inventoryservice: InventoryService

    @GetMapping("/inventory")
    fun getInventory(): ResponseEntity<Invetory> {
        return ResponseEntity(inventoryservice.inventoryRepository.findAll().first(), HttpStatus.OK)

    }

}
