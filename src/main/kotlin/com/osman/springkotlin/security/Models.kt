package com.osman.springkotlin.security

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class Product(@Id var id: String?, val name: String, val description: String, val unitPrice: Int, val totalQuantity: Int)


@Document
data class CartProduct(@Id var id: String?, val Product: Product, val price: Int, val quantity: Int)



