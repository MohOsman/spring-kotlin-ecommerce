package com.osman.springkotlin.security

import junit.framework.Assert.assertNotNull
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.MediaType
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import java.util.*


@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class CartEndPointTest {


    @Autowired lateinit var mockMvc: MockMvc

    @Mock
    lateinit var prducctService: ProductService

    @Test
    fun   `test adding one product to cart success`() {


        mockMvc.perform(MockMvcRequestBuilders

                .post("/addToCart/{productId}/{quantity}","9bbb6249-f9ef-45db-99dd-311f6ed3496f" , 1))
                .andExpect(MockMvcResultMatchers.status().isCreated)
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
    }


    private fun getTestProduct(): Optional<Product> = Optional.of(Product("Adidas JBL 2X10", "Latest Adidas", 450, 100))
}