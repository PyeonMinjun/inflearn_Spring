package hello.itemservice.web.basic;

import hello.itemservice.domain.Item;
import hello.itemservice.domain.ItemRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@RequestMapping("/basic/items")
@RequiredArgsConstructor
@Controller
public class BasicItemController {

    private final ItemRepository itemRepository = new ItemRepository();


    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "basic/items";
    }


    @GetMapping("/{itemId}")
    public String item(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/item";
    }

    @GetMapping("/add")       // 상품등록
    public String form() {
        return "basic/addForm";
    }

    //    -----------------------------------------------------------상품등록처리 시작
    //    @PostMapping("/add")       // 상품 등록 처리
    public String addItemV1(@RequestParam String itemName,
                            @RequestParam Integer price,
                            @RequestParam Integer quantity,
                            Model model) {

        Item item = new Item();
        item.setItemName(itemName);
        item.setPrice(price);
        item.setQuantity(quantity);
        itemRepository.save(item);
        model.addAttribute("item", item);

        return "basic/item";
    }

//    @PostMapping("/add")
    public String addItemV2(@ModelAttribute("item") Item item, Model model) {
        itemRepository.save(item);
        model.addAttribute("item", item); // 자동 추가, 생략가능
        return "basic/item";
    }

    //    @PostMapping("/add")
    public String addItemV3(@ModelAttribute Item item) {
        itemRepository.save(item);
        //Item -> item 이 model에 담긴다.
        return "basic/item";
    }

    //    @PostMapping("/add")
    public String addItemV4(Item item) {
        itemRepository.save(item);
        return "basic/item";
    }


//    @PostMapping("/add")
    public String addItemV5(Item item) {
        itemRepository.save(item);
        return "redirect:basic/items" + item.getId();
    }

    @PostMapping("/add")
    public String addItemV6(Item item, RedirectAttributes redirectAttributes) {
        Item saveItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", saveItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:basic/items/{itemId}";
    }


    //    -----------------------------------------------------------상품등록처리 끝

    //수정 폼
    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);

        model.addAttribute("item", item);
        return "/basic/editForm";
    }

    //수정 처리
    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId ,@ModelAttribute Item item) {
        itemRepository.update(itemId, item);
        return "redirect:/basic/items/{itemId}";
    }


//
//
//
//
//
//
//
//
//
// 수정 폼
//    @GetMapping("/{itemId}/edit")
//    public String editForm(@PathVariable Long itemId, Model model) { // 어떤상품을 수정하지 필요
//        Item item = itemRepository.findById(itemId);
//        model.addAttribute("item", item);
//        return "/basic/editForm";
//    }
//
//    // 수정 처리
//    @PostMapping("/{itemId}/edit")
//    public String edit(@PathVariable Long itemId, @ModelAttribute Item item) {
//        itemRepository.update(itemId, item);
//        return "redirect:/basic/items/{itemId}}";





    /**
     * 테스트용 데이터 추가
     */
    @PostConstruct
    public void init() {

        itemRepository.save(new Item("testA", 1000, 10));
        itemRepository.save(new Item("testB", 2000, 20));
    }
}
