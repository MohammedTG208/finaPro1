package spring.boot.fainalproject.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import spring.boot.fainalproject.Model.Order;
import spring.boot.fainalproject.Model.Product;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    Order findOrderById(Integer id);

    @Query("SELECT o FROM Order o WHERE o.id = ?1 AND o.customer_orders.id = ?2")
    Order findByOrderIdAndCustomerId(Integer orderId, Integer customerId);

    @Query("SELECT o FROM Order o WHERE o.id = ?1 AND o.facility_orders.id = ?2")
    Order findOrderByIdAndFacilityId(Integer orderId, Integer facilityId);

    @Query("SELECT o FROM Order o WHERE o.facility_orders.id = ?1")
    List<Order> findOrdersByFacilityId(Integer facilityId);

    @Query("SELECT o FROM Order o WHERE o.customer_orders.id = ?1")
    List<Order> findOrdersByCustomerId(Integer customerId);

    @Query("SELECT COUNT(o) FROM Order o WHERE o.facility_orders.id = ?1")
    Integer countOrdersByFacilityId(Integer facilityId);

    @Query("SELECT COUNT(o) FROM Order o WHERE o.customer_orders.id = ?1")
    Integer countOrdersByCustomerId(Integer customerId);

    @Query("SELECT o FROM Order o JOIN o.products p WHERE p.supplier.id = ?1 AND o.orderedDate = ?2")
    List<Order> findOrdersBySupplierIdAndDate(Integer supplierId, LocalDate date);


    @Query("SELECT o FROM Order o JOIN o.products p WHERE p.supplier.id = ?1 AND o.id = ?2")
    Order findOrderForSupplier(Integer supplierId, Integer orderId);



}
