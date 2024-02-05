package hr.algebra.shopapp.controller;

import hr.algebra.shopapp.model.ItemType;

import hr.algebra.shopapp.repository.TypeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private TypeRepo itemTypeRepository;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("itemTypes", itemTypeRepository.findAll());
        return "/category/category_list"; // Path to the template that lists item types
    }

    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("itemType", new ItemType());
        return "/category/create"; // Path to the template for creating a new item type
    }

    @PostMapping("/create")
    public String create(@ModelAttribute ItemType itemType, RedirectAttributes redirectAttributes) {
        itemTypeRepository.save(itemType);
        redirectAttributes.addFlashAttribute("successMessage", "Item type created successfully!");
        return "redirect:/category";
    }

    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        ItemType itemType = itemTypeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid item type Id:" + id));
        model.addAttribute("itemType", itemType);
        return "/category/edit"; // Path to the template for editing an existing item type
    }

    @PostMapping("/edit/{id}")
    public String update(@PathVariable Long id, @ModelAttribute ItemType updatedItemType, RedirectAttributes redirectAttributes) {
        itemTypeRepository.findById(id)
                .map(itemType -> {
                    itemType.setName(updatedItemType.getName());
                    return itemTypeRepository.save(itemType);
                }).orElseThrow(() -> new IllegalArgumentException("Item type not found with id " + id));

        redirectAttributes.addFlashAttribute("successMessage", "Item type updated successfully!");
        return "redirect:/category";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        ItemType itemType = itemTypeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid item type Id:" + id));
        itemTypeRepository.delete(itemType);
        redirectAttributes.addFlashAttribute("successMessage", "Item type deleted successfully!");
        return "redirect:/category";
    }
}

