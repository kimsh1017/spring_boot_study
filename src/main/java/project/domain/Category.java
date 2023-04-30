package project.domain;

import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.ArrayList;

import project.domain.item.Item;

@Entity
@Getter @Setter
public class Category{
    @Id @GeneratedValue
    @Column(name = "category_id")
    private Long id;
    
    private String name;
    
    @ManyToMany
    @JoinTable(name = "category_item",
              joinColumns = @JoinColumn(name = "category_id"),
              inverseJoinColumns = @JoinColumn(name = "item_id")
              )
    private List<Item> items = new ArrayList<>();
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Category parent;
    
    @OneToMany(mappedBy = "parent")
    private List<Category> child = new ArrayList<> ();
    
    public void addChildCategory(Category child){
        this.child.add(child);
        child.setParent(this);
    }
}