package osman.springktolin.com.springbootkotlin.security

import org.springframework.data.mongodb.core.mapping.Document

@Document
data class Bag(val products :  List<Product>) {

}
