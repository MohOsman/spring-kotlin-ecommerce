package osman.springktolin.com.springbootkotlin.security

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import org.springframework.stereotype.Service

@Service
class CartService @Autowired constructor(val productService: ProductService, val cartRepository: CartRepository) {



    fun getProduct(id: String) = cartRepository.findById(id).orElseThrow { NotFoundException("Product with id $id   do not exsit in the cart") }

    fun getProducts(): List<OrderProduct> = cartRepository.findAll();

    fun addToCart(productID : String, quantity : Int = 1 ) : OrderProduct {
      val product = productService.getProduct(productID)
      val totalPrice =  product.price.times(quantity)
      val orderProduct = OrderProduct(product.name, product.description, totalPrice, quantity)
     return  cartRepository.save(orderProduct)


        // TODO Add for error case
    }
}


@Repository
interface CartRepository : MongoRepository<OrderProduct, String> {


}
