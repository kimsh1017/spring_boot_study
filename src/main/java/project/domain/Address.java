package project.domain;

import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
public class Address{
    private String city;
    private String street;
    private String zipcode;
    
    //JPA 스펙상 엔티티, 임베디드 -> public, protected로 생성자 있어야함
    protected Address() {
    }
    
    public Address(String city, String street, String zipcode){
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}