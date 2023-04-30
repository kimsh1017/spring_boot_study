package project.repository;

import project.domain.Order;
import project.domain.OrderItem;
import project.domain.Member;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.ArrayList;
import lombok.RequiredArgsConstructor;
import javax.persistence.criteria.*;
import javax.persistence.TypedQuery;
import org.springframework.util.StringUtils;

@Repository
@RequiredArgsConstructor
public class OrderRepository{
    
    private final EntityManager em;
    
    public void save(Order order){
        em.persist(order);
    }
    
    public Order findOne(Long id){
        return em.find(Order.class, id);
    }
    
    //QueryDSL 버전
    // public List<Order> findAll(OrderSearch orderSearch){
        
    //     QOrder order = QOrder.orderSearch;
    //     QMember member = QMember.member;
        
        
    //     return query
    //         .select(order)
    //         .from(order)
    //         .join(order.member,member)
    //         .where(statusEq(orderSearch.getOrderStatus()),
    //               nameLike(orderSearch.getMemberName()))
    //         .limit(1000)
    //         .fetch();
    // }
    
    // private BooleanExpression statusEq(OrderStatus statusCond){
    //     if (statusCond == null){
    //         return null;
    //     }
    //     return order.status.eq(statusCond);
    // }
    // private BooleanExpression nameLike(String nameCond){
    //     if(!StringUtils.hasText(nameCond)){
    //         return null;
    //     }
    //     return member.name.like(nameCond);
    // }
    
    public List<Order> findAllByCriteria(OrderSearch orderSearch) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Order> cq = cb.createQuery(Order.class);
        Root<Order> o = cq.from(Order.class);
        Join<Order, Member> m = o.join("member", JoinType.INNER); //회원과 조인
        List<Predicate> criteria = new ArrayList<>();
         //주문 상태 검색
        if (orderSearch.getOrderStatus() != null) {
            Predicate status = cb.equal(o.get("status"),
            orderSearch.getOrderStatus());
            criteria.add(status);
        }
         //회원 이름 검색
        if (StringUtils.hasText(orderSearch.getMemberName())) {
            Predicate name = cb.like(m.<String>get("name"), "%" + orderSearch.getMemberName() + "%");
            criteria.add(name);
        }
        cq.where(cb.and(criteria.toArray(new Predicate[criteria.size()])));
        TypedQuery<Order> query = em.createQuery(cq).setMaxResults(1000); //최대 1000건
        return query.getResultList();
    }
}