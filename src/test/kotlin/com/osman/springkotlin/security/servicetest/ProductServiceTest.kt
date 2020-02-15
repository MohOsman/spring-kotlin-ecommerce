package com.osman.springkotlin.security.servicetest


import com.osman.springkotlin.security.*
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentMatchers
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
internal class ProductServiceTest {

    @Mock
    private lateinit var productRepository: ProductRepository

    @InjectMocks
    lateinit var productService: ProductService


    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

    }

    @Test(expected = IllegalArgumentException::class)
    fun `test create a product that already exist fail`() {
        `when`(productRepository.findById(ArgumentMatchers.anyString())).thenReturn(getTestProduct(15))
        productService.create(getTestProduct(15).get())
        verify(productRepository, times(0)).save(any<Product>())

    }

    @Test
    fun `test adding product sucsess`() {
        `when`(productRepository.save(any<Product>())).thenReturn(getTestProduct(15).get())
        val returnedproduct = productService.create(getTestProduct(15).get())

        verify(productRepository, times(1)).save(any<Product>())
        assertEquals(returnedproduct.name, getTestProduct(15).get().name)
    }

    @Test
    fun `test get all products`() {
        `when`(productRepository.findAll()).thenReturn(listOf(getTestProduct(15).get(), getTestProduct2().get()))
        val products = productService.getAll()

        verify(productRepository, times(1)).findAll()
        assertNotNull(products)
        assertEquals(2, products.size)

    }

    @Test
    fun `test deleting product success`() {
        `when`(productRepository.existsById(ArgumentMatchers.anyString())).thenReturn(true)
        productService.delete(getTestProduct().get().id!!)
        verify(productRepository, times(1)).deleteById(ArgumentMatchers.anyString())
        assertEquals(0, productService.getAll().size)

    }





}
