package spring.boot.fainalproject.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Set;

@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Facility_Request")
public class FacilityRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "  roduct Name cannot be null")
    @Size(min = 3, max = 20, message = " product Name must be between 2 and 20 characters")
    @Column(columnDefinition = "varchar(20) not null")
    private String productName;
    @NotNull(message = " quantity cannot be null" )
    @Column(columnDefinition = "int not null")
    private int quantity;

    @Column(columnDefinition = "varchar(10) ")
    @Pattern(regexp = "PENDING|APPROVED|REJECTED|CANCELED", message = "Status must be either PENDING|APPROVED|REJECTED|CANCELED")
    private String status;

    @NotBlank(message = " description cannot be null")
    @Column(columnDefinition = "varchar(1000) not null")
    private String description;

    @ManyToOne
    @JsonIgnore
    private Facility facility;


    @OneToMany(cascade = CascadeType.ALL,mappedBy = "facilityRequest")
    private Set<Offer> offers;
}
