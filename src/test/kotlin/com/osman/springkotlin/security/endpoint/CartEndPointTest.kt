package com.osman.springkotlin.security.endpoint

import com.osman.springkotlin.security.CartRepository
import com.osman.springkotlin.security.CartService
import com.osman.springkotlin.security.ProductService
import com.osman.springkotlin.security.getTestProduct
import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers


@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class CartEndPointTest {


    @Mock
    lateinit var cartRepository: CartRepository

    @Mock
    lateinit var productService: ProductService

    @InjectMocks
    lateinit var cartService: CartService

    @Autowired
    lateinit var mockMvc: MockMvc


    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Ignore
    @Test
    fun `test adding one product to cart success`() {
        Mockito.`when`(productService.getProduct(ArgumentMatchers.anyString())).thenReturn(getTestProduct(100).get())
        mockMvc.perform(MockMvcRequestBuilders
                .post("/addToCart/{productId}/{quantity}", "9b9b6249-f9ef-45db-99dd-311f6ed3496f", 1))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))

    }


}