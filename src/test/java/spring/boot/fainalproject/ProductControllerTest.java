package spring.boot.fainalproject;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import spring.boot.fainalproject.Controller.ProductController;
import spring.boot.fainalproject.Model.Product;
import spring.boot.fainalproject.Service.ProductService;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = ProductController.class , excludeAutoConfiguration = {SecurityAutoConfiguration.class})
public class ProductControllerTest {

    @MockBean
    private ProductService productService;

    @Autowired
    private MockMvc mockMvc;

    private Product product1, product2;
    private List<Product> products;

    @BeforeEach
    void setUp() {
        product1 = new Product(1, "Product1", 100.0, 10, "Description1", "Category1", "image1.jpg", null, null);
        product2 = new Product(2, "Product2", 200.0, 20, "Description2", "Category2", "image2.jpg", null, null);
        products = Arrays.asList(product1, product2);
    }

    @Test
    public void testGetAllProducts() throws Exception {
        Mockito.when(productService.getAllProducts()).thenReturn(products);
        mockMvc.perform(get("/api/v1/product/get/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(2)))
                .andExpect(jsonPath("$[0].productName").value("Product1"))
                .andExpect(jsonPath("$[1].productName").value("Product2"));
    }

    @Test
    public void testGetProductById() throws Exception {
        Mockito.when(productService.getProductById(1)).thenReturn(product1);
        mockMvc.perform(get("/api/v1/product/getProductById/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productName").value("Product1"));
    }


}
