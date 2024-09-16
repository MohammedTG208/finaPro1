package spring.boot.fainalproject.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import spring.boot.fainalproject.DTO.CustomerDTO;
import spring.boot.fainalproject.Model.User;
import spring.boot.fainalproject.Service.CustomerService;

@RestController
@RequestMapping("/api/v1/customer")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    @GetMapping("/get-all")
    public ResponseEntity getAllCustomers() {
        return ResponseEntity.status(200).body(customerService.getAllCustomers());
    }

    @PostMapping("/register")
    public ResponseEntity registerCustomer(@Valid @RequestBody CustomerDTO customerDTO) {
        customerService.registerCustomer(customerDTO);
        return ResponseEntity.status(200).body("Register successfully");
    }

    @PutMapping("/update")
    public ResponseEntity updateProfile(@AuthenticationPrincipal User user, @Valid @RequestBody CustomerDTO customerDTO) {
        customerService.updateCustomer(customerDTO,user.getId());
        return ResponseEntity.status(200).body("Update successfully");
    }

    @DeleteMapping("/delete")
    public ResponseEntity deleteCustomer(@AuthenticationPrincipal User user ) {
        customerService.deleteCustomer(user.getId());
        return ResponseEntity.status(200).body("Delete successfully");
    }

    @GetMapping("/customer-details")
    public ResponseEntity getCustomerDetails(@AuthenticationPrincipal User user) {
        return ResponseEntity.status(200).body(customerService.displayCustomerDetails(user.getId()));
    }
}

