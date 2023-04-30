// package project.service;

// import project.repository.OrderRepository;
// import project.repository.MemberRepository;
// import project.repository.ItemRepository;
// import project.domain.*;
// import project.domain.item.Item;
// import project.domain.item.Book;
// import org.junit.Test;
// import org.junit.runner.RunWith;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.test.context.junit4.SpringRunner;
// import org.springframework.transaction.annotation.Transactional;
// import javax.persistence.EntityManager;
// import project.exception.NotEnoughStockException;

// import static org.junit.Assert.*;



// @RunWith(SpringRunner.class)
// @SpringBootTest
// @Transactional
// public class OrderServiceTest{
    
//     @Autowired EntityManager em;
//     @Autowired OrderService orderService;
//     @Autowired OrderRepository orderRepository;
    
//     @Test
//     public void 상품주문() throws Exception{
//         //given
//         Member member = createMember();
//         Book book = createBook();
//         int orderCount = 3;
        
//         //when
//         Long orderId = orderService.order(member.getId(), book.getId(), orderCount);
        
//         //then 
//         Order getOrder = orderRepository.findOne(orderId);
//         assertEquals("상품 주문시 상태는 ORDER", OrderStatus.ORDER, getOrder.getStatus());
//         assertEquals("주문 상품 종류 수는 1개", 1, getOrder.getOrderItems().size());
//         assertEquals("주문 전체 가격은 가격 * 개수", 10000*3, getOrder.getTotalPrice());
//         assertEquals("주문 수량 만큼 재고가 줄어야함", 7, book.getStockQuantity());
//     }
    
//     @Test
//     public void 주문취소() throws Exception{
//         //given
//         Member member = createMember();
//         Book book = createBook(); 
//         int orderCount = 3;
        
//         Long orderId = orderService.order(member.getId(), book.getId(), orderCount);
        
//         //when
//         orderService.cancelOrder(orderId);
        
//         //then
//         Order getOrder = orderRepository.findOne(orderId);
        
//         assertEquals("주문 취소시 상태는 CANCEL", OrderStatus.CANCEL, getOrder.getStatus());
//         assertEquals("주문 취소 상품은 재고가 복구되어야 함", 10, book.getStockQuantity());
//     }
    
//     @Test(expected = NotEnoughStockException.class)
//     public void 상품주문_재고수량초과() throws Exception{
//         //given
//         Member member = createMember();
//         Book book = createBook();
//         int orderCount = 11;
//         //when
//         Long orderId = orderService.order(member.getId(), book.getId(), orderCount);
//         //then
//         fail("재고 수량 부족 예외가 발생해야 합니다.");
//     }
    
//     private Member createMember(){
//         Member member = new Member();
//         member.setName("회원 1");
//         member.setAddress(new Address("서울", "경기", "123-456"));
//         em.persist(member);
//         return member;
//     }
    
//     private Book createBook(){
//         Book book = new Book();
//         book.setName("JPA TEST BOOK");
//         book.setPrice(10000);
//         book.setStockQuantity(10);
//         em.persist(book);
//         return book;
//     }
// }