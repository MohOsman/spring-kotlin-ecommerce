package com.osman.springkotlin.ecommerce.servicetest


import com.osman.springkotlin.ecommerce.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.*
import kotlin.test.assertEquals


@RunWith(JUnit4::class)
internal class CartServiceTest {

    @Mock
    lateinit var cartRepository: CartRepository

    @Mock
    lateinit var productService: ProductService

    @Mock
    lateinit var inventoryService: InventoryService

    @InjectMocks
    private lateinit var cartService: CartService


    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun `test adding one product to cart success`() {

        Mockito.`when`(inventoryService.getProductQuantity(ArgumentMatchers.anyString())).thenReturn(200)
        Mockito.`when`(productService.getProduct(ArgumentMatchers.anyString())).thenReturn(getTestProduct().get())
        Mockito.`when`(cartRepository.save(Mockito.any<ShoppingCart>())).thenReturn(getTestShoppingCart(2, getTestProduct().get()))
        val shoppingCart = cartService.addTodCart(getTestProduct().get().id!!, 2)

        assertEquals(shoppingCart.lineProducts[0].quantity, 2)
        assertEquals(shoppingCart.totalPrice, getTestProduct().get().unitPrice * 2)

        Mockito.verify(cartRepository, Mockito.times(1)).save(Mockito.any())
        Mockito.verify(inventoryService, Mockito.times(1)).getProductQuantity(Mockito.anyString())
        Mockito.verify(productService, Mockito.times(1)).getProduct(Mockito.anyString())

    }

    @Test
    fun `test adding multiple identical products to cart`() {

        Mockito.`when`(inventoryService.getProductQuantity(ArgumentMatchers.anyString())).thenReturn(200)
        Mockito.`when`(productService.getProduct(ArgumentMatchers.anyString())).thenReturn(getTestProduct().get())
        Mockito.`when`(cartRepository.findAll()).thenReturn(mutableListOf(getTestShoppingCart(3, getTestProduct().get())))
        Mockito.`when`(cartRepository.count()).thenReturn(1)
        Mockito.`when`(cartRepository.save(Mockito.any<ShoppingCart>())).thenReturn(getTestShoppingCart(3, getTestProduct().get()))

        val shoppingCart = cartService.addTodCart(getTestProduct().get().id!!, 3)

        assertEquals(shoppingCart.lineProducts[0].quantity, 3)
        assertEquals(shoppingCart.totalPrice, getTestProduct().get().unitPrice * 3)

        Mockito.verify(cartRepository, Mockito.times(1)).save(Mockito.any())
        Mockito.verify(inventoryService, Mockito.times(1)).getProductQuantity(Mockito.anyString())
        Mockito.verify(productService, Mockito.times(0)).getProduct(Mockito.anyString())

    }


    // TODO add errorCases for shopping cart

    @Test
    fun `test removing  whole shopping cart succuess`() {
        Mockito.`when`(inventoryService.getProductQuantity(ArgumentMatchers.anyString())).thenReturn(200)
        Mockito.`when`(productService.getProduct(ArgumentMatchers.anyString())).thenReturn(getTestProduct().get())
        Mockito.`when`(cartRepository.findAll()).thenReturn(mutableListOf(getTestShoppingCart(2, getTestProduct().get())))
        Mockito.`when`(cartRepository.count()).thenReturn(1)
        Mockito.`when`(cartRepository.save(Mockito.any<ShoppingCart>())).thenReturn(getTestShoppingCart(2, getTestProduct().get()))

        val shoppingCart = cartService.addTodCart(getTestProduct().get().id!!, 2)
        cartService.removeActiveCart(shoppingCart)

        Mockito.verify(cartRepository, Mockito.times(1)).save(Mockito.any())
        Mockito.verify(inventoryService, Mockito.times(1)).getProductQuantity(Mockito.anyString())
        Mockito.verify(productService, Mockito.times(0)).getProduct(Mockito.anyString())
        Mockito.verify(cartRepository, Mockito.times(1)).delete(Mockito.any<ShoppingCart>())


    }
}

