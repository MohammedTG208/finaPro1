package spring.boot.fainalproject.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "supplier")
public class Supplier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank(message = "Email cannot be null")
    @Email(message = "Must be a valid email format")
    @Column(columnDefinition = "varchar(100) not null unique")
    private String email;

    @NotNull(message = "commercial Register shouldn't be null")
    @Column(columnDefinition = "varchar(255) not null unique")
    private String commercialRegister;

    @NotNull(message = "license Number shouldn't be null")
    @Column(columnDefinition = "varchar(255) not null unique")
    private String licenseNumber;

    @Column(columnDefinition = "varchar(10) not null unique")
    @Pattern(regexp = "^05\\d{8}$",message = "Phone number most be as 05XXXXXXXX")
    private String phoneNumber;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    // One-to-many relationship with Offer
    @OneToMany(mappedBy = "supplier")
    private Set<Offer> offers;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "supplier_recycle")
    private Set<RecyclingRequest> recyclingRequests;

    @OneToMany(mappedBy = "supplier", cascade = CascadeType.ALL) // OneToMany relationship with Product
    private Set<Product> products;

    @ManyToOne
    @JsonIgnore
    private PriceOffer supplier_price_offer;

}
