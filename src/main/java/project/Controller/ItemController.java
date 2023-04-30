package project.Controller;

import project.domain.Address;
import project.domain.item.Item;
import project.domain.item.Book;
import project.service.ItemService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import java.util.List;     

@Controller
@RequiredArgsConstructor
public class ItemController{
    
    private final ItemService itemService;
    
    @GetMapping(value = "/items/new")
    public String createForm(Model model){
        model.addAttribute("form", new BookForm());
        return "items/createItemForm";
    }
    
    @PostMapping(value = "/items/new")
    public String create(BookForm form){
        
        Book book = new Book();
        book.setName(form.getName());
        book.setPrice(form.getPrice());
        book.setStockQuantity(form.getStockQuantity());
        book.setAuthor(form.getAuthor());
        book.setIsbn(form.getIsbn());

        itemService.saveItem(book);
        return "redirect:/items";
    }
    
    @GetMapping(value = "/items")
    public String list(Model model){
        
        List<Item> items = itemService.findItems();
        model.addAttribute("items",items);
        return "items/itemList";
    }
    
    @GetMapping(value = "/items/{itemId}/edit")
    public String updateItemForm(@PathVariable("itemId") Long itemId, Model model){
        
        Book item = (Book) itemService.findOne(itemId);
        
        BookForm form = new BookForm();
        form.setId(item.getId());
        form.setName(item.getName());
        form.setPrice(item.getPrice());
        form.setStockQuantity(item.getStockQuantity());
        form.setAuthor(item.getAuthor());
        form.setIsbn(item.getIsbn());
        
        model.addAttribute("form",form);
        return "items/updateItemForm";
    }
    
    @PostMapping(value = "/items/{itemId}/edit")
    public String updateItem(@PathVariable("itemId") Long itemId, @ModelAttribute("form") BookForm form){
        
        // //준영속 엔티티 -> merge 사용
        // Book book = new Book();
        
        // book.setId(form.getId());
        // book.setName(form.getName());
        // book.setPrice(form.getPrice());
        // book.setStockQuantity(form.getStockQuantity());
        // book.setAuthor(form.getAuthor());
        // book.setIsbn(form.getIsbn());
        
        // itemService.saveItem(book);
        
        // drity checking 사용
        itemService.updateItem(itemId, form.getName(),form.getPrice(),form.getStockQuantity());
        
        return "redirect:/items";    
    }
}