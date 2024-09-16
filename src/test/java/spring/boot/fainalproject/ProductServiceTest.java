package spring.boot.fainalproject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import spring.boot.fainalproject.API.ApiException;
import spring.boot.fainalproject.Model.Product;
import spring.boot.fainalproject.Model.Supplier;
import spring.boot.fainalproject.Model.User;
import spring.boot.fainalproject.Repository.AuthRepository;
import spring.boot.fainalproject.Repository.ProductRepository;
import spring.boot.fainalproject.Repository.SupplierRepository;
import spring.boot.fainalproject.Service.ProductService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private SupplierRepository supplierRepository;

    @Mock
    private AuthRepository authRepository;

    private Product product1, product2;
    private Supplier supplier;
    private User user;
    private List<Product> products;

    @BeforeEach
    void setUp() {
        // Initialize Supplier with default constructor and set properties
        supplier = new Supplier();
        supplier.setId(1);
        supplier.setEmail("supplier@example.com");
        supplier.setCommercialRegister("123456");
        supplier.setLicenseNumber("ABC123");
        supplier.setPhoneNumber("0512345678");
        // Set the user and other relationships if needed
        // supplier.setUser(user); // Set user if needed

        // Initialize User
        user = new User();
        user.setId(1);
        user.setUsername("user");
        user.setPassword("password");
        user.setRole("ADMIN");

        // Initialize Products
        product1 = new Product();
        product1.setId(1);
        product1.setProductName("Product1");
        product1.setPrice(10.0);
        product1.setQuantity(5);
        product1.setDescription("Description1");
        product1.setImgURL("url1");
        product1.setCategory("Category1");
        product1.setSupplier(supplier);

        product2 = new Product();
        product2.setId(2);
        product2.setProductName("Product2");
        product2.setPrice(20.0);
        product2.setQuantity(10);
        product2.setDescription("Description2");
        product2.setImgURL("url2");
        product2.setCategory("Category2");
        product2.setSupplier(supplier);

        products = new ArrayList<>();
        products.add(product1);
        products.add(product2);
    }



    @Test
    public void getAllProductsTest() {
        when(productRepository.findAll()).thenReturn(products);
        List<Product> productList = productService.getAllProducts();
        assertEquals(2, productList.size());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    public void getProductByIdTest() {
        when(productRepository.findProductById(product1.getId())).thenReturn(product1);
        Product foundProduct = productService.getProductById(product1.getId());
        assertEquals(product1, foundProduct);
        verify(productRepository, times(1)).findProductById(product1.getId());
    }

    @Test
    public void addProductTest() {
        when(supplierRepository.findSupplierById(supplier.getId())).thenReturn(supplier);
        productService.addProduct(supplier.getId(), product2);
        verify(supplierRepository, times(1)).findSupplierById(supplier.getId());
        verify(productRepository, times(1)).save(product2);
    }

    @Test
    public void deleteProductTest() {
        when(productRepository.findProductById(product1.getId())).thenReturn(product1);
        when(authRepository.findUserById(user.getId())).thenReturn(user);
        productService.deleteProduct(product1.getId(), user.getId());
        verify(productRepository, times(1)).findProductById(product1.getId());
        verify(authRepository, times(1)).findUserById(user.getId());
        verify(productRepository, times(1)).delete(product1);
    }

    @Test
    public void getProductByIdNotFoundTest() {
        when(productRepository.findProductById(999)).thenReturn(null);
        ApiException thrown = assertThrows(ApiException.class, () -> productService.getProductById(999));
        assertEquals("Product not found", thrown.getMessage());
    }

    @Test
    public void addProductSupplierNotFoundTest() {
        when(supplierRepository.findSupplierById(supplier.getId())).thenReturn(null);
        ApiException thrown = assertThrows(ApiException.class, () -> productService.addProduct(supplier.getId(), product2));
        assertEquals("Supplier not found", thrown.getMessage());
    }

    @Test
    public void deleteProductNoAccessTest() {
        when(productRepository.findProductById(product1.getId())).thenReturn(product1);
        when(authRepository.findUserById(user.getId())).thenReturn(user);
        ApiException thrown = assertThrows(ApiException.class, () -> productService.deleteProduct(product1.getId(), user.getId()));
        assertEquals("you did not have accesses to delete this product", thrown.getMessage());
    }
}
