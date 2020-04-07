package com.osman.springkotlin.ecommerce

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class Product(@Id var id: String?, val name: String, val description: String, val unitPrice: Int)

@Document
data class Invetory(@Id val id: String, val InventoryHash: Map<String, Int>)


data class LineProduct(@Id val id: String, val product: Product, val quantity: Int, val price: Int)


data class ShoppingCart(@Id val id: String, val lineProducts: MutableList<LineProduct>, val totalPrice: Int)

@Document
data class Order(@Id val id: String,
                 val orderDate: String,
                 val products: List<HashMap<String, Int>>,
                 val shipmentAddress: ShipmentAddress,
                 val paymentMethod: PaymentMethod,
                 val orderStatus: OrderStatus,
                 val email: String)

enum class OrderStatus {
    PAYED,
    SHIPPED,
    INPROGRESS,
    DELIVERD,


}


data class ShipmentAddress(
        val name: String,
        val streetAddress: String,
        val zipCode: String, val city:
        String, val country: String)


enum class PaymentMethod {
    CARD, CREDIT, SWISH, PAYPAL
}
