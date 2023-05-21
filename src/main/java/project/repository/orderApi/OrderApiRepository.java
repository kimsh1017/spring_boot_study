package project.repository.orderApi;

import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import java.util.List;
import lombok.RequiredArgsConstructor;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class OrderApiRepository{
    
    private final EntityManager em;
    
    public List<OrderApiDto> findOrderApiDtos(){
        List<OrderApiDto> result = findOrders();
        
        result.forEach(o -> {
            List<OrderItemApiDto> orderItems = findOrderItems(o.getOrderId());
            o.setOrderItems(orderItems);
        });
        return result;
    }
    
    public List<OrderApiDto> findOrderApiDtos_advanced1(){
        List<OrderApiDto> result = findOrders();
        
        // id 뽑기
        List<Long> orderIds = result.stream()
            .map(o -> o.getOrderId())
            .collect(Collectors.toList());
        
        //id 사용해서 in 절로 조회
        List<OrderItemApiDto> orderItems = em.createQuery(
            "select new project.repository.orderApi.OrderItemApiDto(oi.order.id, i.name, oi.orderPrice, oi.count)" +
            " from OrderItem oi" +
            " join oi.item i" +
            " where oi.order.id in :orderIds", OrderItemApiDto.class)
            .setParameter("orderIds", orderIds)
            .getResultList();
        
        //맵에다가 id와 함께 저장
        Map<Long, List<OrderItemApiDto>> orderItemMap = orderItems.stream()
            .collect(Collectors.groupingBy(orderItemApiDto -> orderItemApiDto.getOrderId()));
        
        //Map 이용해서 연관관계 연결
        result.forEach(o -> o.setOrderItems(orderItemMap.get(o.getOrderId())));
        return result;
    }
    
    public List<OrderFlatDto> findOrderApiDtos_advanced2(){
        return em.createQuery(
            "select new project.repository.orderApi.OrderFlatDto(o.id, m.name, o.orderDate, o.status, d.address, i.name, oi.orderPrice, oi.count)" +
            " from Order o" +
            " join o.member m" +
            " join o.delivery d" +
            " join o.orderItems oi" +
            " join oi.item.i", OrderFlatDto.class)
            .getResultList();
    }
    
    
    private List<OrderItemApiDto> findOrderItems(Long orderId){
        return em.createQuery(
            "select new project.repository.orderApi.OrderItemApiDto(oi.order.id, i.name, oi.orderPrice, oi.count)" +
            " from OrderItem oi" +
            " join oi.item i" +
            " where oi.order.id = :orderId", OrderItemApiDto.class)
            .setParameter("orderId", orderId)
            .getResultList();
    }
    
    private List<OrderApiDto> findOrders() {
        return em.createQuery(
            "select new project.repository.orderApi.OrderApiDto(o.id, m.name, o.orderDate, o.status, d.address)" +
            " from Order o" + 
            " join o.member m" +
            " join o.delivery d", OrderApiDto.class )
            .getResultList();
    }
}