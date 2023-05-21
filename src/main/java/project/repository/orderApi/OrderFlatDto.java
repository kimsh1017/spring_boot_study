package project.repository.orderApi;

import lombok.Data;
import project.domain.*;
import java.time.LocalDateTime;

@Data
public class OrderFlatDto{
    
    private Long orderId;
    private String name;
    private LocalDateTime orderDate;
    private OrderStatus orderStatus;
    private Address address;
    
    private String itemName;
    private int orderPrice;
    private int count;
    
    
}