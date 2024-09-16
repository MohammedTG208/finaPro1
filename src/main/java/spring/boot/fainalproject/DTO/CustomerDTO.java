package spring.boot.fainalproject.DTO;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CustomerDTO {

    @NotEmpty(message = "username cannot be empty")
    private String username;

    @NotBlank(message = "Name cannot be null")
    @Size(min = 2, max = 20, message = "Name must be between 2 and 20 characters")
    private String name;

    @NotEmpty(message = "password cannot be empty")
    private String password;

    @NotBlank(message = "Role cannot be null")
    @Pattern(regexp = "CUSTOMER|FACILITY|SUPPLIER|ADMIN", message = "Role must be either CUSTOMER, FACILITY, SUPPLIER or ADMIN")
    private String role;

    @NotBlank(message = "Email cannot be null")
    @Email(message = "Must be a valid email format")
    private String email;

    @NotBlank(message = "Phone number cannot be null")
    @Pattern(regexp = "^05\\d{8}$", message = "Phone number must start with '05' and be 10 digits long")
    private String phoneNumber;
}

