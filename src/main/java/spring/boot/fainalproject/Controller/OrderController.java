package spring.boot.fainalproject.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import spring.boot.fainalproject.Model.Order;
import spring.boot.fainalproject.Model.User;
import spring.boot.fainalproject.Service.OrderService;

@RestController
@RequestMapping("/api/v1/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @GetMapping("/get-order-by-userId/{orderId}")
    public ResponseEntity findOrderByUserId(@PathVariable Integer orderId, @AuthenticationPrincipal User user) {
        return ResponseEntity.status(200).body(orderService.getOrderById(orderId, user.getId()));
    }

    @GetMapping("/get-all-by-userId")
    public ResponseEntity findAllByUserId(@AuthenticationPrincipal User user) {
        return ResponseEntity.status(200).body(orderService.getAllOrder(user.getId()));
    }

    @PostMapping("/add-order/{productId}")
    public ResponseEntity addOrder(@PathVariable Integer productId, @Valid @RequestBody Order order, @AuthenticationPrincipal User user) {
        orderService.addNewOrder(productId,order, user.getId());
        return ResponseEntity.status(200).body("order added successfully");
    }

    @PutMapping("/update-by-userId/{orderId}")
    public ResponseEntity updateOrderById(@Valid @RequestBody Order order,@PathVariable Integer orderId, @AuthenticationPrincipal User user) {
        orderService.updateOrder(order,orderId, user.getId());
        return ResponseEntity.status(200).body("order updated successfully");
    }

    @DeleteMapping("/delete/{orderId}")
    public ResponseEntity deleteOrderById(@AuthenticationPrincipal User user, @PathVariable Integer orderId) {
        orderService.deleteOrder(orderId, user.getId());
        return ResponseEntity.status(200).body("order deleted successfully");
    }

    @PutMapping("/supplier-shipping-order/{orderId}")
    public ResponseEntity supplierShippingOrder(@AuthenticationPrincipal User user,@PathVariable Integer orderId) {
        orderService.SupplierShippedOrder(user.getId(), orderId);
        return ResponseEntity.status(200).body("supplier shipped order successfully");
    }

    @GetMapping("/recent-order-supplier")
    public ResponseEntity recentOrderSupplier(@AuthenticationPrincipal User user) {
        return ResponseEntity.status(200).body(orderService.getTodaysOrdersForSupplier(user.getId()));
    }

}

