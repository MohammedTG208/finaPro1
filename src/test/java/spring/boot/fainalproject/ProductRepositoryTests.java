package spring.boot.fainalproject;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import spring.boot.fainalproject.Model.Product;
import spring.boot.fainalproject.Model.Supplier;
import spring.boot.fainalproject.Model.User;
import spring.boot.fainalproject.Repository.AuthRepository;
import spring.boot.fainalproject.Repository.ProductRepository;
import spring.boot.fainalproject.Repository.SupplierRepository;

import java.util.List;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ProductRepositoryTests {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private  AuthRepository authRepository;

    private Product product1;
    private Product product2;
    private Supplier supplier;
    @Autowired
    private SupplierRepository supplierRepository;

    @BeforeEach
    public void setUp() {
        // Create a User object
        User user = new User();
        user.setName("supplier Name");
        user.setUsername("supplierUser");
        user.setPassword("password");
        user.setRole("SUPPLIER");

        // Create a Supplier object
        supplier = new Supplier();
        supplier.setEmail("supplier@example.com");
        supplier.setCommercialRegister("12345");
        supplier.setLicenseNumber("54321");
        supplier.setPhoneNumber("0501234567");

        // Link the user and supplier
        supplier.setUser(user);
        user.setSupplier(supplier);

        // Save the User (cascades to Supplier)
        authRepository.save(user);
        supplierRepository.save(supplier);
        // Create Product objects and associate them with the Supplier
        product1 = new Product();
        product1.setProductName("Product 1");
        product1.setPrice(100.0);
        product1.setQuantity(10);
        product1.setDescription("Description 1");
        product1.setCategory("Electronics");
        product1.setImgURL("product1.jpg");
        product1.setSupplier(supplier);
        productRepository.save(product1);

        product2 = new Product();
        product2.setProductName("Product 2");
        product2.setPrice(200.0);
        product2.setQuantity(20);
        product2.setDescription("Description 2");
        product2.setCategory("Clothing");
        product2.setImgURL("product2.jpg");
        product2.setSupplier(supplier);
        productRepository.save(product2);
    }


    @Test
    public void findProductByCategoryTest() {
        List<Product> products = productRepository.findProductByCategory("Electronics");
        Assertions.assertThat(products).isNotEmpty();
        Assertions.assertThat(products.get(0).getCategory()).isEqualTo("Electronics");
    }

    @Test
    public void findProductBySupplierIDTest() {
        List<Product> products = productRepository.findProductBySupplierID(supplier.getId());
        Assertions.assertThat(products).isNotEmpty();
        Assertions.assertThat(products.get(0).getSupplier().getId()).isEqualTo(supplier.getId());
    }
}
