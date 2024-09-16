package spring.boot.fainalproject;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import spring.boot.fainalproject.API.ApiException;
import spring.boot.fainalproject.Model.Order;
import spring.boot.fainalproject.Model.Product;
import spring.boot.fainalproject.Model.User;
import spring.boot.fainalproject.Repository.*;
import spring.boot.fainalproject.Service.OrderService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderService orderService;

    @Test
    public void getAllOrderTest_FacilityOrders() {

        Integer userId = 1;
        List<Order> mockOrders = Arrays.asList(new Order(), new Order());
        when(orderRepository.findOrdersByFacilityId(userId)).thenReturn(mockOrders);


        List<Order> orders = orderService.getAllOrder(userId);


        assertEquals(2, orders.size());
        verify(orderRepository, times(1)).findOrdersByFacilityId(userId);
        verify(orderRepository, never()).findOrdersByCustomerId(anyInt());
    }

    @Test
    public void getAllOrderTest_CustomerOrders() {

        Integer userId = 2;
        List<Order> mockOrders = Arrays.asList(new Order(), new Order());  // Sample mock data
        when(orderRepository.findOrdersByFacilityId(userId)).thenReturn(Collections.emptyList());
        when(orderRepository.findOrdersByCustomerId(userId)).thenReturn(mockOrders);


        List<Order> orders = orderService.getAllOrder(userId);


        assertEquals(2, orders.size());
        verify(orderRepository, times(1)).findOrdersByCustomerId(userId);  // Verify it was called once
        verify(orderRepository, times(1)).findOrdersByFacilityId(userId);  // Verify the facility method was called
    }

    @Test
    public void getAllOrderTest_NoOrders() {

        Integer userId = 3;
        when(orderRepository.findOrdersByFacilityId(userId)).thenReturn(Collections.emptyList());
        when(orderRepository.findOrdersByCustomerId(userId)).thenReturn(Collections.emptyList());


        ApiException thrown = assertThrows(ApiException.class, () -> {
            orderService.getAllOrder(userId);
        });
        assertEquals("You don't have any orders", thrown.getMessage());
    }
}
