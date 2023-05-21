package project.domain;

import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.List;
import java.util.ArrayList;
import javax.validation.constraints.NotEmpty;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Getter @Setter
public class Member{
    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;
    
    @NotEmpty
    private String name;
    
    @Embedded
    private Address address;
    
    @JsonIgnore
    @OneToMany(mappedBy = "member") //읽기 전용
    private List<Order> orders = new ArrayList<>();
}