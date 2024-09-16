package spring.boot.fainalproject;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import spring.boot.fainalproject.Model.Customer;
import spring.boot.fainalproject.Model.User;
import spring.boot.fainalproject.Repository.AuthRepository;
import spring.boot.fainalproject.Repository.CustomerRepository;

import java.util.List;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CustomerRepositoryTests {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AuthRepository authRepository;

    private Customer customer1, customer2;
    private User customeruser, uCustomer2;

    @BeforeEach
    void setUp() {
        customer1 = new Customer(null, "0546787653", "osama_S@example.com", null, null);
        customeruser = new User(null, "osama1", "Osama Saeed", "Password123@", "CUSTOMER", null, customer1, null);
        customer1.setUser(customeruser);

        customer2 = new Customer(null, "0567345623", "Mohammed.t@example.com", null, null);
        uCustomer2 = new User(null, "mohammed", "Mohammed T", "Password183@", "CUSTOMER", null, customer2, null);
        customer2.setUser(uCustomer2);
    }


    @Test
    public void findCustomerById() {
        //Save users
        authRepository.save(customeruser);
        authRepository.save(uCustomer2);

        // Save Customer entities
        customerRepository.save(customer1);
        customerRepository.save(customer2);

        // Fetch and assert
        Customer customer = customerRepository.findCustomerById(customer1.getId());
        Assertions.assertThat(customer).isEqualTo(customer1);
    }
}
