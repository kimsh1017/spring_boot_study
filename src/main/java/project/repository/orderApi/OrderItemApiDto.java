package project.repository.orderApi;

import lombok.Data;

@Data
public class OrderItemApiDto{
    
    private Long orderId;
    private String itemName;
    private int orderPrice;
    private int count;
    
    public OrderItemApiDto(Long orderId, String itemName, int orderPrice, int count){
        this.orderId =orderId;
        this.itemName = itemName;
        this.orderPrice = orderPrice;
        this.count = count;
    }
}