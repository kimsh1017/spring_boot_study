package project.service;

import project.repository.ItemRepository;
import project.domain.item.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService{
    
    private final ItemRepository itemRepository;
    
    @Transactional
    public void saveItem(Item item){
        itemRepository.save(item);
    }
    
    public List<Item> findItems(){
        return itemRepository.findAll();
    }
    
    public Item findOne(Long id){
        return itemRepository.findOne(id);
    }
    
    @Transactional
    public void updateItem(Long itemId, String name, int price, int stockQuantity){
        Item findItem = itemRepository.findOne(itemId);
        findItem.setName(name);
        findItem.setPrice(price);
        findItem.setStockQuantity(stockQuantity);
    }
}