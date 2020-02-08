package osman.springktolin.com.springbootkotlin.security

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.lang.IllegalArgumentException

@Service
class ProductService @Autowired constructor(val productRepository: ProductRepository) {

    // Todo add some validation to the logic
    fun create(product: Product): Product {
        if (productRepository.findById(product.id).isPresent) {
            throw  IllegalArgumentException("person with the id  ${product.id} already exist");
        }
        return productRepository.save(product)
    }

    fun getProduct(id: String): Product = productRepository.findById(id).orElseThrow { -> NotFoundException("Product does not exist ") }

    fun deleteAll() = productRepository.deleteAll()

    fun getAll() = productRepository.findAll()


}