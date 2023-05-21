package project.api;

import project.domain.*;
import project.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.Data;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.stream.Collectors;
import project.repository.orderApi.*;

@RestController
@RequiredArgsConstructor
public class OrderApiController{
    
    private final OrderRepository orderRepository;
    private final OrderApiRepository orderApiRepository;
    
    // 무한 루프 오류, JsonIgnore 엔티티에 추가해야하는 문제
    @GetMapping("/api/v1/orders")
    public List<Order> ordersV1(){
        List<Order> all = orderRepository.findAllByCriteria(new OrderSearch());
        
        //LazyLoding 강제 초기화
        for (Order order : all){
            order.getMember().getName();
            order.getDelivery().getAddress();
            
            List<OrderItem> orderItems = order.getOrderItems();
            orderItems.stream().forEach(o -> o.getItem().getName());
        }
        return all;
    }
    
    @GetMapping("/api/v2/orders")
    public List<OrderDto> ordersV2(){
        List<Order> orders = orderRepository.findAllByCriteria(new OrderSearch());
        
        List<OrderDto> collect = orders.stream()
            .map(o -> new OrderDto(o))
            .collect(Collectors.toList());
        
        return collect;
    }
    
    @GetMapping("/api/v3.1/orders")
    public List<OrderDto> ordersV3_page(@RequestParam(value = "offset", defaultValue = "0") int offset,
                                  @RequestParam(value = "limit", defaultValue = "100") int limit){
        List<Order> orders = orderRepository.findAllWithMemberDelivery(offset,limit);
        List<OrderDto> collect = orders.stream()
            .map(o -> new OrderDto(o))
            .collect(Collectors.toList());
        
        return collect;
    }
    
    @GetMapping("/api/v3/orders")
    public List<OrderDto> ordersV3(){
        List<Order> orders = orderRepository.findAllWithOrderItem();
        List<OrderDto> collect = orders.stream()
            .map(o -> new OrderDto(o))
            .collect(Collectors.toList());
        
        return collect;
    }
    
    @GetMapping("/api/v4/orders")
    public List<OrderApiDto> ordersV4(){
        return orderApiRepository.findOrderApiDtos();
    }
    
    @GetMapping("/api/v5/orders")
    public List<OrderApiDto> ordersV5(){
        return orderApiRepository.findOrderApiDtos_advanced1();
    }
    
    @GetMapping("/api/v6/orders")
    public List<OrderFlatDto> ordersV6(){
        return orderApiRepository.findOrderApiDtos_advanced2();
    }
    
    
    @Data
    static class OrderDto{
        
        private Long orderId;
        private String name;
        private LocalDateTime orderDate;
        private OrderStatus orderStatus;
        private Address address;
        private List<OrderItemDto> orderItems;
        
        public OrderDto(Order order){
            orderId = order.getId();
            name = order.getMember().getName();
            orderDate = order.getOrderDate();
            orderStatus = order.getStatus();
            address = order.getDelivery().getAddress();
            
            orderItems = order.getOrderItems().stream()
                .map(orderItem -> new OrderItemDto(orderItem))
                .collect(Collectors.toList());
        }
    }
    
    @Data
    static class OrderItemDto{
        private String itemName;
        private int orderPrice;
        private int count;
        
        public OrderItemDto(OrderItem orderItem){
            itemName = orderItem.getItem().getName();
            orderPrice = orderItem.getOrderPrice();
            count = orderItem.getCount();
        }
    }
}