package com.osman.springkotlin.ecommerce.endpoint

import org.junit.Ignore
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Ignore
class ProductEndPointTest {


    @Autowired
    lateinit var mockMvc: MockMvc


    @Test
    @Ignore
    fun `test deleting product success return 200 Ok`() {
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/delete/{id}", "611816ef-8f18-43f3-b74b-ffe7a1c549c7"))
                .andExpect(MockMvcResultMatchers.status().isOk)

    }

    @Test
    @Ignore
    fun `test deleting product fail return 40+0`() {
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/delete/{id}", "611816ef-8f18-43f3-b74b-sdaasdas"))
                .andExpect(MockMvcResultMatchers.status().isNotFound)

    }


}