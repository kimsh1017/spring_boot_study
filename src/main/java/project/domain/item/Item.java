package project.domain.item;

import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;
import project.exception.NotEnoughStockException;
import project.domain.Category;
import java.util.List;
import java.util.ArrayList;

@Entity
@Getter @Setter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
public abstract class Item{
    
    @Id @GeneratedValue
    @Column(name = "item_id")
    private Long id;
    
    private String name;
    
    private int price;
    
    private int stockQuantity;
    
    @ManyToMany(mappedBy="items")
    private List<Category> categories = new ArrayList<>();
    
    // -----비즈니스 로직 ----- 
    
    // 재고 증가
    public void addStock(int quantity) {
        this.stockQuantity += quantity;
    }
    
    public void removeStock(int quantity) {
        int restStock = this.stockQuantity - quantity;
        if (restStock < 0){
            throw new NotEnoughStockException("need more stock");
        }
        this.stockQuantity = restStock;
    }
    
}