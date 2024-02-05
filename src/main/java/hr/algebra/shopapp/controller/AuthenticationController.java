package hr.algebra.shopapp.controller;


import hr.algebra.shopapp.Service.UserService;
import hr.algebra.shopapp.dto.UserDto;
import hr.algebra.shopapp.model.User;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/authentication")
public class AuthenticationController {

    private final UserService userService;

    @GetMapping("/login")
    public String login() {
        return "authentication/login";
    }
    @GetMapping("/afterLogin")
    public String afterLogin(HttpSession session) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        authentication.getAuthorities().forEach(authority -> {
            if (authority.getAuthority().equals("ROLE_ADMIN")) {
                session.setAttribute("role", "admin");
            } else if (authority.getAuthority().equals("ROLE_USER")) {
                session.setAttribute("role", "user");
            }
        });
        return "redirect:/home_page";
    }


    @GetMapping("/register")
    public String registerForm(Model model) {
        UserDto user = new UserDto();
        model.addAttribute("user", user);
        return "authentication/register";
    }

    @PostMapping("/register/save")
    public String registerUser(UserDto user, BindingResult result,Model model) {
        User existingUser = userService.findByEmail(user.getEmail());

        if (existingUser != null && existingUser.getEmail() != null && !existingUser.getEmail().isEmpty()) {
            result.rejectValue("email", null,
                    "There is already an account registered with the same email");
        }

        if (result.hasErrors()) {
            model.addAttribute("user", user);
            return "authentication/register";
        }

        userService.saveUser(user);
//        UserRegistrationEvent registrationEvent = new UserRegistrationEvent(user);
//        eventPublisher.publishEvent(registrationEvent);

        return "redirect:/authentication/login";
    }

    @GetMapping("/users")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String listUsers(Model model) {
        model.addAttribute("users", userService.findAllUsers());
        return "authentication/user_list";
    }
}
