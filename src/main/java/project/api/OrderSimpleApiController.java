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

/**
 * Order
 * Order -> Member
 * Order -> Delivery
*/

@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController{    
    
    private final OrderRepository orderRepository;
    
    // @GetMapping("/api/v1/simple-orders")
    // public List<Order> ordersV1() {
    //     List<Order> all = orderRepository.findAllByCriteria(new OrderSearch());
    //     return all;
    // }
    
    // //N+1 문제 발생함
    // @GetMapping("/api/v2/simple-orders")
    // public List<SimpleOrderDto> ordersV2() {
    //     List<Order> orders = orderRepository.findAllByCriteria(new OrderSearch());
    //     List<SimpleOrderDto> result = orders.stream()
    //         .map(o -> new SimpleOrderDto(o))
    //         .collect(Collectors.toList());
        
    //     return result;
    // }
    
    // // fetch join 사용하기
    // @GetMapping("/api/v3/simple-orders")
    // public List<SimpleOrderDto> ordersV3() {
    //     List<Order> orders = orderRepository.findAllWithMemberDelivery();
    //     List<SimpleOrderDto> result = orders.stream()
    //         .map(o -> new SimpleOrderDto(o))
    //         .collect(Collectors.toList());
        
    //     return result;
    // }
    
    @GetMapping("/api/v4/simple-orders")
    public List<SimpleOrderDto> ordersV4() {
        return orderRepository.findOrderDtos();
    }
}