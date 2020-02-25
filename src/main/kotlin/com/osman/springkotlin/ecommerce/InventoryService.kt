package com.osman.springkotlin.ecommerce

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import org.springframework.stereotype.Service

@Service
class InventoryService @Autowired constructor(val inventoryRepository: InventoryRepository) {


    val inventoryID = "Inventory-2314"

    fun createInventory(invetory: Invetory): Invetory {
        return inventoryRepository.save(invetory)

    }

    fun getInventory(id: String): Invetory {
        return inventoryRepository.findById(id).orElseThrow {
            NotFoundException("Inventory does not exist ")
        }

    }

    fun getProductQuantity(id: String): Int? {
        val inventory = getInventory(inventoryID)
        if (inventory.InventoryHash.containsKey(id)) {
            return inventory.InventoryHash[id]

        }
        throw  NotFoundException("Product id doesnt exist in inventory ")

    }
}

@Repository
interface InventoryRepository : MongoRepository<Invetory, String>
