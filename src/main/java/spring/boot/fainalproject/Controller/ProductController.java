package spring.boot.fainalproject.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import spring.boot.fainalproject.Model.Product;
import spring.boot.fainalproject.Model.User;
import spring.boot.fainalproject.Service.ProductService;

@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    // Get all products
    @GetMapping("/get/all")
    public ResponseEntity getAllProducts() {
        return ResponseEntity.status(200).body(productService.getAllProducts());
    }

    // Get a product by ID
    @GetMapping("/getProductById/{id}")
    public ResponseEntity getProductById(@PathVariable Integer id) {
        return ResponseEntity.status(200).body(productService.getProductById(id));
    }

    // Add a new product by a Supplier
    @PostMapping("/add-new")
    public ResponseEntity addProduct(@AuthenticationPrincipal User user, @Valid @RequestBody Product product) {
        productService.addProduct(user.getId(), product);
        return ResponseEntity.status(201).body("Product added successfully");
    }

    // Update an existing product by Supplier
    @PutMapping("/updateProduct/{id}")
    public ResponseEntity updateProduct(@PathVariable Integer id,
                                        @RequestParam Integer supplierId,
                                        @Valid @RequestBody Product productDetails) {
        productService.updateProduct(id, productDetails, supplierId);
        return ResponseEntity.status(200).body("Product updated successfully");
    }

    // Delete a product
    @DeleteMapping("/deleteProduct/{id}")
    public ResponseEntity deleteProduct(@PathVariable Integer id,@AuthenticationPrincipal User user) {
        productService.deleteProduct(id, user.getId());
        return ResponseEntity.status(200).body("Product deleted successfully");
    }
    //search product
    //this is new......
    @GetMapping("/search-product/{keyWord}")
    public ResponseEntity searchProduct(@PathVariable String keyWord) {
        return ResponseEntity.status(200).body(productService.searchProduct(keyWord));
    }

    // get the best-selling product for a supplier
    //this new also......
    @GetMapping("/best-seller")
    public ResponseEntity getBestSellingProduct(@AuthenticationPrincipal User user) {
        return ResponseEntity.status(200).body(productService.getBestSellingProductBySupplier(user.getId()));
    }

    //search by category
    //new endpoint
    @GetMapping("/search-by-category/{category}")
    public ResponseEntity getProductsByCategory(@PathVariable String category) {
        return ResponseEntity.status(200).body(productService.searchByCategory(category));
    }

}