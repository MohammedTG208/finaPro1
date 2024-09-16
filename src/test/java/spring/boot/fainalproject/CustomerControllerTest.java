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
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import spring.boot.fainalproject.Controller.CustomerController;
import spring.boot.fainalproject.DTO.CustomerDTO;
import spring.boot.fainalproject.Model.Customer;
import spring.boot.fainalproject.Model.User;
import spring.boot.fainalproject.Service.CustomerService;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = CustomerController.class, excludeAutoConfiguration = {SecurityAutoConfiguration.class})
public class CustomerControllerTest {

    @MockBean
    CustomerService customerService;

    @Autowired
    MockMvc mockMvc;

    Customer customer1, customer2;
    User user;
    CustomerDTO customerDTO;

    @BeforeEach
    void setUp() {
        user = new User(1, "osama", "Osama saeed", "password", "CUSTOMER",null,null,null);
        customer1 = new Customer(1, "0501234567", "osama@example.com", user, null);
        customerDTO = new CustomerDTO("osama", "Osama saeed", "password", "CUSTOMER", "osama@example.com", "0501234567");
    }

    @Test
    public void getAllCustomers() throws Exception {
        List<Customer> customers = Arrays.asList(customer1);
        Mockito.when(customerService.getAllCustomers()).thenReturn(customers);

        mockMvc.perform(get("/api/v1/customer/get-all"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].email").value("osama@example.com"));
    }

    @Test
    public void registerCustomer() throws Exception {
        Mockito.doNothing().when(customerService).registerCustomer(Mockito.any(CustomerDTO.class));

        mockMvc.perform(post("/api/v1/customer/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(customerDTO)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Register successfully"));
    }

    @Test
    public void updateProfile() throws Exception {
        Mockito.doNothing().when(customerService).updateCustomer(Mockito.any(CustomerDTO.class), Mockito.anyInt());

        mockMvc.perform(put("/api/v1/customer/update")
                        .principal(() -> user.getUsername())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(customerDTO)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Update successfully"));
    }
}
