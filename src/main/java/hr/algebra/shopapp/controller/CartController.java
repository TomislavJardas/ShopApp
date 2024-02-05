package hr.algebra.shopapp.controller;


import hr.algebra.shopapp.Service.CartService;
import hr.algebra.shopapp.model.Cart;
import hr.algebra.shopapp.model.Item;
import hr.algebra.shopapp.model.SelectedItem;
import hr.algebra.shopapp.repository.ItemRepo;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    @Autowired
    private CartService cartService;

    private final ItemRepo itemRepo;

    @GetMapping
    public String viewCart(HttpSession session,Model model) {
        model.addAttribute("cartItems", getOrCreateCart(session));
        model.addAttribute("total", cartService.getTotalPrice());
        return "cart"; // Name of the cart view template
    }


    @PostMapping("/update")
    public String updateCart(@RequestParam("itemId") Long itemid, int quantity, HttpSession session) {
        Item item = itemRepo.findById(itemid).orElseThrow();

        Cart cart = getOrCreateCart(session);
        cart.updateQuantity(item, quantity);
        session.setAttribute("cart", cart);
        return "redirect:/cart";
    }

    @GetMapping("/remove/{itemId}")
    public String removeItemFromCart(@PathVariable Long itemId, HttpSession session) {
        Item item = itemRepo.findById(itemId).orElseThrow();

        Cart cart = getOrCreateCart(session);
        cart.removeItem(item);
        session.setAttribute("cart", cart);
        return "redirect:/cart";
    }

    @PostMapping("/clear")
    public String clearCart() {
        cartService.clearCart();
        return "redirect:/cart";
    }
    @PostMapping("/add")
    public String addToCart(@RequestParam("itemId")Long itmeid, int quantity, HttpSession session) {
        Item item = itemRepo.findById(itmeid).orElseThrow();
        item.setQuantity(quantity);

        Cart cart = getOrCreateCart(session);
        cart.addItem(item, quantity);
        session.setAttribute("cart", cart);
        return "redirect:/cart";
    }
    public static Cart getOrCreateCart(HttpSession session) {
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) {
            cart = new Cart();
            session.setAttribute("cart", cart);
        }
        return cart;
    }
}
