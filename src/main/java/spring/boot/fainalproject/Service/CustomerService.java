package spring.boot.fainalproject.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import spring.boot.fainalproject.API.ApiException;
import spring.boot.fainalproject.DTO.CustomerDTO;
import spring.boot.fainalproject.Model.Customer;
import spring.boot.fainalproject.Model.User;
import spring.boot.fainalproject.Repository.AuthRepository;
import spring.boot.fainalproject.Repository.CustomerRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final AuthRepository authRepository;

    public List<Customer> getAllCustomers() {
        if (customerRepository.findAll().isEmpty()){
            throw new ApiException("No customers found in DB");
        }else {
            return customerRepository.findAll();
        }
    }

    public Customer displayCustomerDetails(Integer id) {
        return customerRepository.findCustomerById(id);
    }

    public void registerCustomer(CustomerDTO customerDTO){
        User user = new User();
        user.setUsername(customerDTO.getUsername());
        user.setName(customerDTO.getName());
        String hash = new BCryptPasswordEncoder().encode(customerDTO.getPassword());
        user.setPassword(hash);
        user.setRole("CUSTOMER");

        authRepository.save(user);

        Customer customer = new Customer();
        customer.setEmail(customerDTO.getEmail());
        customer.setPhoneNumber(customerDTO.getPhoneNumber());

        customer.setUser(user);
        customerRepository.save(customer);

    }

    public void updateCustomer(CustomerDTO customerDTO,Integer customerId){
        Customer customer = customerRepository.findCustomerById(customerId);
        if(customer == null){
            throw new ApiException("Customer not found to updated");
        }

            User user = customer.getUser();
            user.setUsername(customerDTO.getUsername());
            user.setName(customerDTO.getName());
            String hash = new BCryptPasswordEncoder().encode(customerDTO.getPassword());
            user.setPassword(hash);
            user.setRole("CUSTOMER");

            customer.setPhoneNumber(customerDTO.getPhoneNumber());
            customer.setEmail(customerDTO.getEmail());

            user.setCustomer(customer);

            customerRepository.save(customer);
            authRepository.save(user);

    }

    public void deleteCustomer(Integer customerId){
       Customer customer = customerRepository.findCustomerById(customerId);
       if(customer == null){
           throw new ApiException("Customer not found to deleted");
       }
       User user = customer.getUser();
       authRepository.delete(user);
       customerRepository.delete(customer);
    }
}

