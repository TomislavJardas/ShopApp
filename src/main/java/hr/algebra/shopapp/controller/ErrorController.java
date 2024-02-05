package hr.algebra.shopapp.controller;


import jakarta.servlet.http.HttpServletRequest;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller

public class ErrorController {

    @RequestMapping("/custom-error")
    public String handleError(@RequestParam String errorMessage, HttpServletRequest request, Model model) {
        model.addAttribute("errorMessage", errorMessage);
        return "error_page";
    }
}
