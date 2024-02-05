package hr.algebra.shopapp.controller;

import hr.algebra.shopapp.model.Item;
import hr.algebra.shopapp.model.ItemType;
import hr.algebra.shopapp.repository.ItemRepo;
import hr.algebra.shopapp.repository.TypeRepo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Controller
@RequestMapping("/item")
public class ItemController {

    private final ItemRepo itemRepo;
    private final TypeRepo typeRepo;


    public ItemController(ItemRepo itemRepo, TypeRepo typeRepo) {
        this.itemRepo = itemRepo;
        this.typeRepo = typeRepo;
    }

    @GetMapping
    public String getAllDinosaurs(Model model) {
        List<Item> item = itemRepo.findAll();
        model.addAttribute("item", item);
        return "item/item_list";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        List<ItemType> types = typeRepo.findAll();
        model.addAttribute("item", new Item());
        model.addAttribute("itemTypes", types);
        return "item/create";
    }

    @PostMapping("/create")
    public String createDinosaur(@ModelAttribute("item") Item item) {
        itemRepo.save(item);
        return "redirect:/item";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        Item item = itemRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid item ID: " + id));
        List<ItemType> types = typeRepo.findAll();
        model.addAttribute("item", item);
        model.addAttribute("itemTypes", types);
        return "item/edit";
    }

    @PostMapping("/edit/{id}")
    public String updateItem(@PathVariable("id") Long id, @ModelAttribute("item") Item updatedItem) {
        Item item = itemRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Item ID: " + id));

        item.setTitle(updatedItem.getTitle());
        item.setDescription(updatedItem.getDescription());
        item.setCategory(updatedItem.getCategory());
        item.setPrice(updatedItem.getPrice());
        item.setQuantity(updatedItem.getQuantity());
        item.setImage(updatedItem.getImage());

        itemRepo.save(item);
        return "redirect:/item";
    }

    @GetMapping("/delete/{id}")
    public String deleteItem(@PathVariable("id") Long id) {
        Item item = itemRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Item ID: " + id));

        itemRepo.delete(item);
        return "redirect:/item";
    }

}
