package spring.boot.fainalproject.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.util.Set;

@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "facility")
public class Facility {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;



    @NotBlank(message = "Email cannot be null")
    @Email(message = "Must be a valid email format")
    @Column(columnDefinition = "varchar(100) not null unique")
    private String email;

    @NotBlank(message = "commerical Register cannot be null")
    @Column(columnDefinition = "varchar(255) not null unique")
    private String commericalRegister;

    @NotBlank(message = "license Number cannot be null")
    @Column(columnDefinition = "varchar(255) not null unique")
    private String licenseNumber;

    @NotBlank(message = "Phone number cannot be null")
    @Pattern(regexp = "^05\\d{8}$", message = "Phone number must start with '05' and be 10 digits long")
    @Column(columnDefinition = "varchar(10) not null unique")
    private String phoneNumber;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @OneToMany(cascade = CascadeType.ALL , mappedBy = "facility")
    private Set<FacilityRequest> facilityRequests;

    @OneToMany(cascade = CascadeType.ALL , mappedBy = "facility_orders")
    private Set<Order> orders;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "facility_recycle")
    @JsonManagedReference
    private Set<RecyclingRequest> recyclingRequests;


}
