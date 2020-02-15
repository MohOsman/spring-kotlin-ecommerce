package com.osman.springkotlin.security.servicetest

import com.osman.springkotlin.security.*
import org.junit.Assert
import org.junit.Before
import org.junit.Test


import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.*


@RunWith(JUnit4::class)
internal class CartServiceTest {

    @Mock
    lateinit var cartRepository: CartRepository

    @Mock
    lateinit var productService: ProductService

    @InjectMocks
    private lateinit var cartService: CartService


    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun `test adding to cart success`() {
        Mockito.`when`(productService.getProduct(ArgumentMatchers.anyString())).thenReturn(getTestProduct(100).get())
        Mockito.`when`(cartRepository.save(Mockito.any<CartProduct>())).thenReturn(getTestCartProduct(2).get())
        val cartProduct = cartService.addToCart(getTestProduct(11).get().id!!)

        Assert.assertEquals(cartProduct.quantity, 2)
        Assert.assertEquals(cartProduct.price, getTestProduct(15).get().unitPrice * 2)
    }


    @Test
    fun `test adding to cart with more quantity than inventory  `() {
        Mockito.`when`(productService.getProduct(ArgumentMatchers.anyString())).thenReturn(getTestProduct(15).get())
        Mockito.`when`(cartRepository.save(Mockito.any<CartProduct>())).thenReturn(getTestCartProduct(15).get())
        val cartProduct = cartService.addToCart(getTestProduct(200).get().id!!)
        Assert.assertEquals(cartProduct.quantity, 15)
        Assert.assertEquals(cartProduct.price, getTestProduct(15).get().unitPrice * 15)
    }


    @Test
    fun `test get cart product by product id`() {
        Mockito.`when`(productService.getProduct(Mockito.anyString())).thenReturn(getTestProduct(20).get())
        Mockito.`when`(cartRepository.findByProductId(Mockito.anyString())).thenReturn(getTestCartProduct())
        val cartProduct = cartService.getCartProductByProductId(getTestProduct().get().id!!)
        Assert.assertEquals(productService.getProduct(getTestProduct().get().id!!).id, cartProduct.Product.id!!)
    }
}

