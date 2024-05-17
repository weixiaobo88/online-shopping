package example.controller;

import org.example.controller.ProductController;
import org.example.model.Product;
import org.example.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ProductControllerUnitTest {

    private ProductService productService = mock(ProductService.class);
    private ProductController productController = new ProductController(productService);
    private MockMvc httpClient = MockMvcBuilders.standaloneSetup(productController).build();


    @Test
    void should_get_products() throws Exception {
        List<Product> productList = Arrays.asList(
                new Product(1L, "http://example.com/image1.jpg", "Product 1", 19.99, 100),
                new Product(2L, "http://example.com/image2.jpg", "Product 2", 29.99, 150)
        );
        given(productService.getProducts(1, 15)).willReturn(productList);

        httpClient.perform(MockMvcRequestBuilders.get("/products")
                        .param("page", "1")
                        .param("size", "15")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].imageUrl").value("http://example.com/image1.jpg"))
                .andExpect(jsonPath("$[0].productName").value("Product 1"))
                .andExpect(jsonPath("$[0].price").value(19.99))
                .andExpect(jsonPath("$[0].stock").value(100));
    }

}



