package spring.boot.fainalproject.Service;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import spring.boot.fainalproject.API.ApiException;
import spring.boot.fainalproject.Model.*;
import spring.boot.fainalproject.Repository.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final AuthRepository authRepository;
    private final FacilityRepository facilityRepository;
    private final ProductRepository productRepository;

    //get facility and customer order
    //extra endpoint 1
    public List<Order> getAllOrder(Integer userId) {
        // Check if there are orders for the facility
        List<Order> facilityOrders = orderRepository.findOrdersByFacilityId(userId);
        if (!facilityOrders.isEmpty()) {
            return facilityOrders;
        }

        // Check if there are orders for the customer
        List<Order> customerOrders = orderRepository.findOrdersByCustomerId(userId);
        if (!customerOrders.isEmpty()) {
            return customerOrders;
        }

        // No orders found for either facility or customer
        throw new ApiException("You don't have any orders");
    }

    //get one facility or customer order
    // extra 2
    public Order getOrderById(Integer orderId, Integer userId) {
        if (orderRepository.findByOrderIdAndCustomerId(orderId, userId) != null) {
            return orderRepository.findByOrderIdAndCustomerId(orderId, userId);
        } else if (orderRepository.findOrderByIdAndFacilityId(orderId, userId) != null) {
            return orderRepository.findOrderByIdAndFacilityId(orderId, userId);
        } else {
            throw new ApiException("You dont have any order by this id: " + orderId);
        }
    }

    //extra endpoint
    // this method change
    // extra 3
    public void addNewOrder(Integer productId, Order order, Integer userId) {
        // Find the user
        User user = authRepository.findUserById(userId);
        if (user == null) {
            throw new ApiException("User not found");
        }

        // Find the product
        Product product = productRepository.findProductById(productId);

        if (product == null) {
            throw new ApiException("Product not found");
        }

        if (order.getQuantity() > product.getQuantity()) {
            throw new ApiException("Order quantity is greater than product quantity");
        }

        // Check the user role
        if (user.getRole().equals("CUSTOMER")) {
            Customer customer = customerRepository.findCustomerById(user.getId());
            order.setCustomer_orders(customer);
        }

        if (user.getRole().equals("FACILITY")) {
            Facility facility = facilityRepository.findFacilityById(user.getId());
            order.setFacility_orders(facility);
        }

        if (order.getProducts() == null) {
            order.setProducts(new HashSet<>()); // Initialize it here
        }

        int totalAmount = 0;
        for (int i = 1; i <= order.getQuantity(); i++) {
            totalAmount += product.getPrice();
        }
        //shipping method
        if (order.getShippingMethod().equals("Priority")) {
            order.setTotalAmount(getDiscount(user.getId(),totalAmount) + 24);
        } else if (order.getShippingMethod().equals("Express")) {
            order.setTotalAmount(getDiscount(user.getId(),totalAmount) + 50);
        } else {
            order.setTotalAmount(getDiscount(user.getId(),totalAmount));
        }

        // Update product quantity and manage relationships
        product.setQuantity(product.getQuantity() - order.getQuantity());
        order.getProducts().add(product);
        order.setOrderStatus("Pending");
        product.getOrders().add(order);

        // Save both order and product
        orderRepository.save(order);
        productRepository.save(product);
    }

    //extra endpoint
    // this method change ......
    // extra 4
    public void updateOrder(Order order, Integer orderId, Integer userId) {
        // Find the existing order
        Order oldOrder = orderRepository.findOrderById(orderId);
        if (oldOrder == null) {
            throw new ApiException("Order not found");
        }

        if (order.getOrderStatus().equals("Shipping")) {
            throw new ApiException("Order status is shipping can not be updated");
        }

        // Find the user
        User user = authRepository.findUserById(userId);
        if (user == null) {
            throw new ApiException("User not found");
        }

        // Find the product
        Product product = productRepository.findProductById(orderId);
        if (product == null) {
            throw new ApiException("Product not found");
        }

        // Calculate the quantity change
        int quantityChange = order.getQuantity() - oldOrder.getQuantity();

        // Check the user role and update accordingly
        if (user.getRole().equalsIgnoreCase("customer")) {
            Customer customer = customerRepository.findCustomerById(userId);
            oldOrder.setCustomer_orders(customer);
        }
        if (user.getRole().equalsIgnoreCase("facility")) {
            Facility facility = facilityRepository.findFacilityById(userId);
            oldOrder.setFacility_orders(facility);
        }

        // Check if the new quantity is valid
        if (quantityChange > 0) {
            // Increase quantity
            if (order.getQuantity() > product.getQuantity() + oldOrder.getQuantity()) {
                throw new ApiException("Order quantity is greater than product quantity");
            }
            // Subtract the new quantity from the product's stock
            product.setQuantity(product.getQuantity() - quantityChange);
        } else {
            // Decrease quantity
            product.setQuantity(product.getQuantity() + (-quantityChange));
        }

        // Calculate total amount
        int totalAmount = 0;
        for (int i = 1; i <= order.getQuantity(); i++) {
            totalAmount += product.getPrice();
        }
        if (order.getShippingMethod().equalsIgnoreCase("Priority")) {
            oldOrder.setTotalAmount(totalAmount + 24);
        } else if (order.getShippingMethod().equalsIgnoreCase("Express")) {
            oldOrder.setTotalAmount(totalAmount + 50);
        } else {
            oldOrder.setTotalAmount(totalAmount);
        }

        // Update the existing order details
        oldOrder.setQuantity(order.getQuantity());
        oldOrder.setProductName(order.getProductName());
        oldOrder.setShippingMethod(order.getShippingMethod());

        // Save the updated order and product
        orderRepository.save(oldOrder);
        productRepository.save(product);
    }

    //
    public void deleteOrder(Integer orderId, Integer userId) {
        Order order = orderRepository.findByOrderIdAndCustomerId(orderId, userId);

        // Check if the order belongs to a customer
        if (order != null) {
            //check status of ordered
            if (order.getOrderStatus().equals("shipped")) {
                throw new ApiException("Order status is shipped can not be Cancel");
            }else {
                // change status of product as cancel
                order.setOrderStatus("Cancel");
                orderRepository.save(order);
            }
        } else {
            // Check if the order belongs to a facility
            order = orderRepository.findOrderByIdAndFacilityId(orderId, userId);
            if (order != null) {
                //check status of ordered
                if (order.getOrderStatus().equals("shipped")) {
                    throw new ApiException("Order status is shipped can not be Cancel");
                }else {
                    // change status of product as cancel
                    order.setOrderStatus("Cancel");
                    orderRepository.save(order);
                }
            } else {
                throw new ApiException("Order not found for the given user (customer or facility).");
            }
        }
    }

    //get discount if customer or facility have more than 5 orders
    //extra 12
    public int getDiscount(Integer userId,int totalAmount) {
        User user1 = authRepository.findUserById(userId);
        Integer totalOrdersHave=0;
        if (user1.getRole().equals("FACILITY")) {
            totalOrdersHave= orderRepository.countOrdersByFacilityId(userId);
        }
        if (user1.getRole().equals("CUSTOMER")) {
            totalOrdersHave= orderRepository.countOrdersByCustomerId(userId);
        }

        if (totalOrdersHave>=5){
            double discount=0.3*totalAmount;
            discount=totalAmount-discount;
            return (int) discount;
        }else {
            return 0;
        }
    }


    //extra 13
    // Method to allow supplier to change the status of their orders if they own all the products
    public void SupplierShippedOrder(Integer supplierId, Integer orderId) {
        Order order = orderRepository.findOrderById(orderId);

        // Check if all products in the order are owned by the same supplier
        boolean allProductsFromSupplier = order.getProducts().stream()
                .allMatch(product -> product.getSupplier().getId()==supplierId);

        if (!allProductsFromSupplier) {
            throw new ApiException("Supplier does not have permission to update this order, as not all products belong to them.");
        }

        if (order.getOrderStatus().equalsIgnoreCase("Cancel") || order.getOrderStatus().equalsIgnoreCase("Shipped")) {
            throw new ApiException("Order status cannot be updated. The order is either canceled or already shipped.");
        }
            order.setOrderStatus("shipped");
            orderRepository.save(order);
    }

    //edite this and try to do it
    public List<Order> getTodaysOrdersForSupplier(Integer supplierId) {
        return orderRepository.findOrdersBySupplierIdAndDate(supplierId, LocalDate.now());
    }
}

