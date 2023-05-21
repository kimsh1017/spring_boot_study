package project.repository.orderApi;

import lombok.Data;
import project.domain.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderApiDto{
    
    private Long orderId;
    private String name;
    private LocalDateTime orderDate;
    private OrderStatus orderStatus;
    private Address address;
    private List<OrderItemApiDto> orderItems;
        
    public OrderApiDto(Long orderId, String name, LocalDateTime orderDate, OrderStatus orderStatus, Address address){
        this.orderId = orderId;
        this.name = name;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.address = address;
    }
}