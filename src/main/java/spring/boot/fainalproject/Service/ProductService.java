package spring.boot.fainalproject.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import spring.boot.fainalproject.API.ApiException;
import spring.boot.fainalproject.Model.Product;
import spring.boot.fainalproject.Model.Supplier;
import spring.boot.fainalproject.Model.User;
import spring.boot.fainalproject.Repository.AuthRepository;
import spring.boot.fainalproject.Repository.ProductRepository;
import spring.boot.fainalproject.Repository.SupplierRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final SupplierRepository supplierRepository;
    private final AuthRepository authRepository;

    // Get all products
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // Get a specific product by ID
    // extra 5
    public Product getProductById(Integer id) {
        Product product = productRepository.findProductById(id);
        if (product == null) {
            throw new ApiException("Product not found");
        }
        return product;
    }

    // Add a new product by Supplier
    // extra 6
    public void addProduct(Integer supplierId, Product product) {
        Supplier supplier = supplierRepository.findSupplierById(supplierId);
        if (supplier == null) {
            throw new ApiException("Supplier not found");
        }
        product.setSupplier(supplier); // Set supplier to the product
        productRepository.save(product);
    }

    // Update an existing product
    // extra 7
    public void updateProduct(Integer id, Product updatedProduct, Integer supplierId) {
        Product product = productRepository.findProductById(id);
        if (product == null) {
            throw new ApiException("Product not found");
        }
        Supplier supplier = supplierRepository.findSupplierById(supplierId);
        if (supplier == null) {
            throw new ApiException("Supplier not found");
        }
        if (product.getSupplier().getId() == supplier.getId()) {
            product.setProductName(updatedProduct.getProductName());
            product.setPrice(updatedProduct.getPrice());
            product.setQuantity(updatedProduct.getQuantity());
            product.setDescription(updatedProduct.getDescription());
            product.setImgURL(updatedProduct.getImgURL());
            product.setCategory(updatedProduct.getCategory());
            product.setSupplier(supplier); // Update the supplier of the product

            productRepository.save(product);
        }else {
            throw new ApiException("Supplier id mismatch ");
        }
    }

    // Delete a product
    public void deleteProduct(Integer id,Integer supplerId) {
        Product product = productRepository.findProductById(id);
        User user = authRepository.findUserById(supplerId);
        if (product == null) {
            throw new ApiException("Product not found");
        } else if (product.getSupplier().getId()==supplerId||user.getRole().equals("ADMIN")) {
            productRepository.delete(product);
        }else {
            throw new ApiException("you did not have accesses to delete this product");
        }
    }

    //search for products by product name
    // extra 8
    public List<Product> searchProduct(String keyword) {
        if (productRepository.findProductByProductName(keyword).isEmpty()){
            throw new ApiException("Product not found");
        }else {
            return productRepository.findProductByProductName(keyword);
        }
    }

    //get product by category
    //extra 10
    public List<Product> searchByCategory(String category) {
        if (productRepository.findProductByCategory(category).isEmpty()){
            throw new ApiException("Product by this category not found");
        }else {
            List<Product> products = productRepository.findProductByCategory(category);
            products.forEach(product -> product.setOrders(null));
            return products;
        }
    }

    // Method to get the best-selling product for a supplier
    //extra 9
    public List<Product> getBestSellingProductBySupplier(Integer supplierId) {
        User User =authRepository.findUserById(supplierId);
        List<Product> products = productRepository.findBestSellingProductBySupplierId(supplierId);
        if (!User.getRole().equals("SUPPLIER")){
            throw new ApiException("you are not Supplier");
        }else {
            // Return the top product if available
            if (products.isEmpty()) {
                throw new ApiException("You dont have product yet");
            }else {
                return  products;
            }
        }
    }

    //Alert supplier if quantity less than 3
    //extra 13
    public List<Product> alertSupplier(Integer supplierId) {
        List<Product> checkQuantity=productRepository.findProductBySupplierID(supplierId);
        List<Product> lessThan =new ArrayList<>();
        for (Product product : checkQuantity) {
            if (product.getQuantity()<=3){
                lessThan.add(product);
            }
        }
        return lessThan;
    }
}