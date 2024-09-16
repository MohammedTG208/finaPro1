package spring.boot.fainalproject.Model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
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
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "varchar(50) not null")
    @NotEmpty(message = "product can not be null")
    private String productName;
    @Column(columnDefinition = "DOUBLE not null")
    @Positive(message = "enter positive price")
    private double price;
    @Column(columnDefinition = "int not null")
    @Positive(message = "Enter valid quantity numbers")
    private int quantity;


    @Column(columnDefinition = "varchar(100) not null")
    @NotEmpty(message = "description can not be null")
    @Size(min = 5, max = 100, message = "max length 100 for description")
    private String description;

    //this new attribute.....
    @NotEmpty(message = "category can not be null")
    private String category;

    @NotEmpty(message = "image requirement")
    @Pattern(regexp = "^.*\\.(jpg|jpeg|png|gif)$", message = "Please provide a valid image URL (jpg, jpeg, png, gif).")
    @Column(columnDefinition = "varchar(255) not null")
    private String imgURL;

    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "products")
    private Set<Order> orders;

    @ManyToOne
    @JsonIgnore
    private Supplier supplier;


}
