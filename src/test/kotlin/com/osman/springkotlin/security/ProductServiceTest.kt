package com.osman.springkotlin.security

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import java.lang.IllegalArgumentException
import java.util.*

@RunWith(JUnit4::class)
internal class ProductServiceTest {

    @Mock
    lateinit var cartRepository: CartRepository
    @Mock
    lateinit var productRepository: ProductRepository


    lateinit var productService: ProductService


    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        this.productService = ProductService(productRepository);

    }

    @Test(expected = IllegalArgumentException::class)
    fun `test create a product that already exist fail`() {
      `when`(productRepository.findById(ArgumentMatchers.anyString())).thenReturn(getTestProduct())
        productService.create(getTestProduct().get())

    }

    private fun getTestProduct(): Optional<Product> = Optional.of(Product("Adidas JBL 2X10", "Latest Adidas", 450, 100))
    private fun getTestCartProduct() : Optional<OrderProduct> = Optional.of(OrderProduct(
            "Adidas JBL 2X10",
            "Latest Adidas",
            getTestProduct()?.get()?.price!! * 2, 2))
}