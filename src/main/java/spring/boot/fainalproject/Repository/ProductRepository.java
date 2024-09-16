package spring.boot.fainalproject.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import spring.boot.fainalproject.Model.Product;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    Product findProductById(Integer id);

    List<Product> findProductByProductName(String productName);

    @Query("SELECT p FROM Product p JOIN p.orders o WHERE p.supplier.id = ?1 GROUP BY p.id ORDER BY SUM(o.quantity) DESC")
    List<Product> findBestSellingProductBySupplierId(Integer supplierId);

    List<Product> findProductByCategory(String category);

    @Query("select pro from Product pro where pro.supplier.id=?1")
    List<Product> findProductBySupplierID(Integer supplierId);
}
