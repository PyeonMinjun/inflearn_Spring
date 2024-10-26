package hello.itemservice.web.basic;


import com.sun.source.tree.UsesTree;
import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * /basic/item 경로로
 * 생성자주입으로 repo 주입 -> 롬복으로 대체 가능
 * get매핑으로 repo에있는거 모두 찾아서 등록 후 뷰 basic/item으로 전송
 *
 * postConstruct로 테스트 값 집어 넣기
 */

@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor
public class BasicItemController {

    private final ItemRepository itemRepository;

    @GetMapping
    public String item(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "/basic/items";

    }

    //상세페이지
    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "/basic/item";
    }

    //상품등록폼 페이지이동
    @GetMapping("/add")
    public String addForm(){
        return "/basic/addForm";
    }

    //상품등록
//    @PostMapping("/add")
    public String save(@RequestParam String itemName,
                       @RequestParam int price,
                       @RequestParam Integer quantity,
                       Model model){
        Item item = new Item();

        item.setItemName(itemName);
        item.setPrice(price);
        item.setQuantity(quantity);
        itemRepository.save(item);

        model.addAttribute("item", item);


        return "/basic/item";
    }

//    @PostMapping("/add")
    public String addItemV2(@ModelAttribute("item") Item item){
        itemRepository.save(item);
//        model.addAttribute("item", item); //자동추가 생략가능
        return "/basic/item";
    }

//    @PostMapping("/add")
    public String addItemV3(@ModelAttribute Item item){

        itemRepository.save(item);
//        model.addAttribute("item", item); //자동추가 생략가능
        return "/basic/item";
    }

    /**
     *
     *모델어트리뷰트 생략, 클래스명으로 소문자로 변환하여 모델객체에 자동으로 담아줌
     *
     */
//    @PostMapping("/add")
    public String addItemV4(Item item){
        itemRepository.save(item);
//        model.addAttribute("item", item); //자동추가 생략가능
        return "/basic/item";
    }
    // PRG 패턴 적용
//    @PostMapping("/add")
    public String addItemV5(Item item){
        itemRepository.save(item);
//        model.addAttribute("item", item); //자동추가 생략가능
        return "redirect:/basic/items/"+ item.getId();
    }


    @PostMapping("/add")
    public String addItemV6(Item item, RedirectAttributes redirectAttributes){
        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/basic/items/{itemId}";
    }



    // 수정 이동
    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "/basic/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @ModelAttribute Item item) {
        itemRepository.update(itemId,item);
        return "redirect:/basic/items/{itemId}";
    }

    /**
     * 테스트용
     */

    @PostConstruct
    public void init() {

        itemRepository.save(new Item("itemA", 10000, 3));
        itemRepository.save(new Item("itemB", 20000, 5));
    }

}
