package hr.algebra.shopapp.controller;

import hr.algebra.shopapp.model.Cart;
import hr.algebra.shopapp.model.SelectedItem;
import hr.algebra.shopapp.model.UserOrder;
import hr.algebra.shopapp.model.User;
import hr.algebra.shopapp.repository.CartRepo;
import hr.algebra.shopapp.repository.LoginRepo;
import hr.algebra.shopapp.repository.UserOrderRepo;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.hibernate.mapping.Selectable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
@RequestMapping("/payment")
public class PaymentController {
    private final UserOrderRepo orderRepo;
    private final CartRepo cartRepo;
    private final LoginRepo userRepo;

    @GetMapping
    public String showPaymentOptions() {
        return "payment_options";
    }

    @PostMapping
    public String processPayment(String paymentOption) {
        if (paymentOption.equals("paypal")) {
            // Logic for PayPal payment
            return "redirect:/payment/paypal"; // Redirect to PayPal payment page
        } else if (paymentOption.equals("instore")) {
            // Logic for in-store payment
            return "redirect:/payment/instore"; // Redirect to in-store payment page
        } else {
            // Invalid payment option selected
            return "error_page"; // Handle the error condition appropriately
        }
    }

    @GetMapping("/paypal")
    public String showPayPalPaymentPage(Model model, HttpSession session) {
        Cart cart = CartController.getOrCreateCart(session);
        model.addAttribute("cart", cart);
        model.addAttribute("totalAmount", cart.getTotal());

        return "paypal_payment";
    }

    @GetMapping("/instore")
    public String placeInStoreOrder(Model model, HttpSession session) {

        try {
            Cart cart = CartController.getOrCreateCart(session);
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            UserOrder order = UserOrder.builder()
                    .totalPrice(cart.getTotal())
                    .paymentMethod("instore")
                    .username(authentication.getName())
                    .delivered(false)
                    .orderDate(LocalDateTime.now())
                    .build();
            orderRepo.save(order);

            for (SelectedItem item : cart.getItems()) {
                item.setOrder(order);
                cartRepo.save(item);
            }

            cart.clearCart();
            session.removeAttribute("cart");
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "error_page";
        }
        return "order_confirm";
    }

    @GetMapping("/orders")
    public String showOrders(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepo.findByEmail(authentication.getName());
        if (user == null) {
            return "error_page";
        }
        if (user.getRoles().get(0).getName().equals("ROLE_ADMIN")) {
            model.addAttribute("orders", orderRepo.findAll());
            return "order/order_list";
        } else {
            model.addAttribute("orders", orderRepo.findAllByUsername(authentication.getName()));
            return "order/user";
        }
    }

    @GetMapping("/items/{id}")
    public String showOrderItems(@PathVariable Long id, Model model) {
        model.addAttribute("items", cartRepo.findAllByOrderId(id));
        return "order/items";
    }

    @GetMapping("/paymentSuccess")
    public String paymentSuccess(HttpSession session, Model model) {

        try {
            Cart cart = CartController.getOrCreateCart(session);
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            UserOrder order = UserOrder.builder()
                    .totalPrice(cart.getTotal())
                    .paymentMethod("paypal")
                    .username(authentication.getName())
                    .delivered(false)
                    .orderDate(LocalDateTime.now())
                    .build();
            orderRepo.save(order);

            for (SelectedItem item : cart.getItems()) {
                item.setOrder(order);
                cartRepo.save(item);
            }

            cart.clearCart();
            session.removeAttribute("cart");
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "error_page";
        }

        return "order_confirm";
    }
}
