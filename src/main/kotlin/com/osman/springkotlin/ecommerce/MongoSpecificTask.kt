package com.osman.springkotlin.ecommerce

import org.springframework.beans.factory.annotation.Autowired

import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.stereotype.Service

@Service
class MongoSpecificTask {

    @Autowired
    lateinit var mongoTemplate: MongoTemplate


    fun dropdatabase() {
        mongoTemplate.db.drop()
    }
}