package spring.boot.fainalproject.DTO;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import spring.boot.fainalproject.Model.PriceOffer;

@Data
@AllArgsConstructor
public class PriceOfferDTO {

    @NotBlank(message = "  roduct Name cannot be null")
    @Size(min = 3, max = 20, message = " product Name must be between 2 and 20 characters")
    private String productName;

    @NotNull(message = " quantity cannot be null" )
    @Column(columnDefinition = "int not null")
    private int quantity;

    @NotBlank(message = " description cannot be null")
    private String description;

    @NotNull(message = " Price cannot be null" )
    private double price;

    @NotBlank(message = "status cannot be null")
    @Pattern(regexp = "PENDING|APPROVED|REJECTED|CANCELED", message = "Status must be either PENDING|APPROVED|REJECTED|CANCELED")
    private String status;
}
