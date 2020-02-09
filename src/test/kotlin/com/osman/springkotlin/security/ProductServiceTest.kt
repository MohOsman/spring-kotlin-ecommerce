package com.osman.springkotlin.security


import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import java.util.*

@RunWith(JUnit4::class)
internal class ProductServiceTest {

    @Mock
    private lateinit var cartRepository: CartRepository
    @Mock
    private lateinit var productRepository: ProductRepository


    lateinit var productService: ProductService


    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        this.productService = ProductService(productRepository)

    }

    @Test(expected = IllegalArgumentException::class)
    fun `test create a product that already exist fail`() {
        `when`(productRepository.findById(ArgumentMatchers.anyString())).thenReturn(getTestProduct())
        productService.create(getTestProduct().get())
        verify(productRepository, times(0)).save(any<Product>())

    }

    @Test
    fun `test adding product sucsess`() {
        `when`(productRepository.save(any<Product>())).thenReturn(getTestProduct().get())
        val returnedproduct = productService.create(getTestProduct().get())

        verify(productRepository, times(1)).save(any<Product>())
        assertEquals(returnedproduct.name, getTestProduct().get().name)
    }

    @Test
    fun `test get all products`() {
        `when`(productRepository.findAll()).thenReturn(listOf(getTestProduct().get(), getTestProduct2().get()))
        val products = productService.getAll()

        verify(productRepository, times(1)).findAll()
        assertNotNull(products)
        assertEquals(2, products.size)

    }

    private fun getTestProduct(): Optional<Product> = Optional.of(Product("Adidas JBL 2X10", "Latest Adidas", 450, 100))
    private fun getTestProduct2(): Optional<Product> = Optional.of(Product("Adidas JBL 2X12", " Most Latest Adidas", 550, 100))
    private fun getTestCartProduct(): Optional<OrderProduct> = Optional.of(OrderProduct(
            "Adidas JBL 2X10",
            "Latest Adidas",
            getTestProduct().get().price * 2, 2))


}
