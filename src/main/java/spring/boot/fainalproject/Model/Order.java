package spring.boot.fainalproject.Model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.*;


import java.time.LocalDate;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "order_product")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotEmpty(message = "product name can not be null")
    @Column(columnDefinition = "varchar(100) not null")
    private String productName;

    @Column(columnDefinition = "int not null")
    @Positive(message = "Enter positive number")
    private int quantity;

    @Positive(message = "Total amount can not be null")
    @Column(columnDefinition = "double not null")
    private double totalAmount;

    @Column(columnDefinition = "enum('Standard','Priority','Express')")
    @Pattern(regexp = "Standard|Priority|Express", message = "Please choose one of the following: 'Standard', 'Priority', or 'Express'.")
    private String shippingMethod;

    //supplier shipped the product
    @Pattern(regexp = "Cancel|Pending|shipped")
    @Column(columnDefinition = "enum('Cancel','pending','Shipped')")
    private String orderStatus;

    @Column(columnDefinition = "varchar(200)")
    private LocalDate orderedDate=LocalDate.now();

    @NotEmpty(message = "image requirement")
    @Pattern(regexp = "^.*\\.(jpg|jpeg|png|gif)$", message = "Please provide a valid image URL (jpg, jpeg, png, gif).")
    @Column(columnDefinition = "varchar(255) not null")
    private String imgURL;

    @ManyToOne
    @JsonIgnore
    private Facility facility_orders;

    @ManyToOne
    @JsonIgnore
    private Customer customer_orders;

    @ManyToMany
    @JsonIgnore
    private Set<Product> products;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private Set<Review> reviews;


}