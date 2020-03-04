package com.osman.springkotlin.ecommerce.servicetest

import com.osman.springkotlin.ecommerce.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.times
import org.mockito.MockitoAnnotations
import java.util.*
import kotlin.test.assertEquals

@RunWith(JUnit4::class)
internal class InventoryServiceTest {

    @Mock
    lateinit var inventoryRepository: InventoryRepository
    @InjectMocks
    lateinit var inventoryService: InventoryService

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun `test  create inventory `() {
        Mockito.`when`(inventoryRepository.save(Mockito.any<Invetory>())).thenReturn(getInventory())
        inventoryService.createInventory(getInventory())

        Mockito.verify(inventoryRepository, times(1)).save(Mockito.any<Invetory>())

    }

    @Test
    fun `test  get inventory products `() {
        Mockito.`when`(inventoryRepository.findById(Mockito.anyString())).thenReturn(Optional.of(getInventory()))
        assertEquals(200, inventoryService.getProductQuantity(getTestProduct().get().id!!))
    }
}