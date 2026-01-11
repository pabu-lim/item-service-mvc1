package hello.itemservice.web.basic;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor
public class BasicItemController {

    private final ItemRepository itemRepository;

    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "basic/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable("itemId") long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/item";
    }

    @GetMapping("/add")
    public String addForm() {
        return "basic/addForm";
    }

//    @PostMapping("/add")
    public String additemV1(@RequestParam("itemName") String itemName, @RequestParam("price") int price, @RequestParam("quantity") Integer quantity,
                       Model model) {
        Item item = new Item(itemName, price, quantity);
        item.setItemName(itemName);
        item.setPrice(price);
        item.setQuantity(quantity);

        itemRepository.save(item);

        model.addAttribute("item", item);

        return "basic/item";
    }

//    @PostMapping("/add")
    public String additemV2(@ModelAttribute("item") Item item) {

//        Item item = new Item(itemName, price, quantity);
//        item.setItemName(itemName);
//        item.setPrice(price);
//        item.setQuantity(quantity);
        // modelAttribute가 다 해줌

        itemRepository.save(item);
//        model.addAttribute("item", item); modelAttribute("item") 이렇게 이름 지정하면 addAttribute까지 같이 해줌.

        return "basic/item";
    }

//    @PostMapping("/add")
    public String additemV3(@ModelAttribute Item item, Model model) {

        itemRepository.save(item);
//        model.addAttribute("item", item); ModelAttribute에 이름까지 생략하면 클래스의 맨 앞 대문자만 소문자로 바꾼 클래스명으로 이름을 넣어준다.

        return "basic/item";
    }

//    @PostMapping("/add")
    public String additemV4(Item item, Model model) {

        itemRepository.save(item);
//        model.addAttribute("item", item); ModelAttribute에 이름까지 생략하면 클래스의 맨 앞 대문자만 소문자로 바꾼 클래스명으로 이름을 넣어준다.

        return "basic/item";
    }

//    @PostMapping("/add")
    public String additemV5(Item item) {
        itemRepository.save(item);
        return "redirect:/basic/items/" + item.getId();
    }

    @PostMapping("/add")
    public String additemV6(Item item, RedirectAttributes redirectAttributes) {

        Item saveditem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", saveditem.getId());
        redirectAttributes.addAttribute("status", true);
//        model.addAttribute("item", item); ModelAttribute에 이름까지 생략하면 클래스의 맨 앞 대문자만 소문자로 바꾼 클래스명으로 이름을 넣어준다.

        return "redirect:/basic/items/{itemId}";
    }

    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @ModelAttribute Item item) {
        itemRepository.update(itemId, item);
        return "redirect:/basic/items/{itemId}";
    }

    /**
     * 테스트용 데이터 추가
     */
    @PostConstruct
    public void init() {
        itemRepository.save(new Item("itemA", 10000, 10));
        itemRepository.save(new Item("itemB", 20000, 20));
    }
}
