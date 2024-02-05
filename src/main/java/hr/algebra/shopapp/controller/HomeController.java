package hr.algebra.shopapp.controller;


import hr.algebra.shopapp.Service.ItemService;
import hr.algebra.shopapp.model.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping
public class HomeController {

    @Autowired
    private ItemService itemService;

    @GetMapping("/home")
    public String home(Model model) {
        List<Item> items = itemService.findAll();
        model.addAttribute("items", items);
        return "home_page";
    }
}
