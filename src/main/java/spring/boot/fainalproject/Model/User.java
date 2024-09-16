package spring.boot.fainalproject.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Integer id;
    @NotEmpty(message = "username cannot be empty")
    @Column(columnDefinition = "varchar(15) not null unique")
    private String username;
    @NotBlank(message = "Name cannot be null")
    @Size(min = 2, max = 20, message = "Name must be between 2 and 20 characters")
    @Column(columnDefinition = "varchar(20) not null")
    private String name;
    @NotEmpty(message = "password cannot be empty")
    @Column(columnDefinition = "varchar(100) not null unique")
    private String password;
    @NotBlank(message = "Role cannot be null")
    @Pattern(regexp = "CUSTOMER|FACILITY|SUPPLIER|ADMIN", message = "Role must be either CUSTOMER, FACILITY, SUPPLIER or ADMIN")
    @Column(columnDefinition = "varchar(12) not null")
    private String role;

    @OneToOne(cascade = CascadeType.ALL ,mappedBy = "user")
    @PrimaryKeyJoinColumn
    private Facility facility;
//
    @OneToOne
    @PrimaryKeyJoinColumn
    private Customer customer;
//
    @OneToOne
    @PrimaryKeyJoinColumn
    private Supplier supplier;



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(this.role));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}