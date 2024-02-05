package hr.algebra.shopapp.startData;

import hr.algebra.shopapp.model.Item;
import hr.algebra.shopapp.model.ItemType;
import hr.algebra.shopapp.model.Role;
import hr.algebra.shopapp.model.User;
import hr.algebra.shopapp.repository.ItemRepo;
import hr.algebra.shopapp.repository.LoginRepo;
import hr.algebra.shopapp.repository.TypeRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
@RequiredArgsConstructor
public class AppStarter implements CommandLineRunner {

    private final LoginRepo userRepo;

    private final ItemRepo itemRepo;
    private final TypeRepo typeRepo;
    @Override
    public void run(String... args) {
        User admin = new User();
        admin.setName("Admin Admin");
        admin.setEmail("TestAdmin@admin.com");
        admin.setPassword("$2a$12$IJVUnpE5fabjd.lBBsKjxOT8ceSETAnQUv7KH.1clEaCOa19HeIc2");
        Role roleAdmin = new Role();
        roleAdmin.setName("ROLE_ADMIN");

        admin.setRoles(List.of(roleAdmin));
        userRepo.save(admin);

        User user = new User();
        user.setName("User User");
        user.setEmail("TestUser@user.com");
        user.setPassword("$2a$12$IJVUnpE5fabjd.lBBsKjxOT8ceSETAnQUv7KH.1clEaCOa19HeIc2");
        Role roleUser = new Role();
        roleUser.setName("ROLE_USER");

        user.setRoles(List.of(roleUser));
        userRepo.save(user);



        ItemType mens = new ItemType();
        mens.setName("men's clothing");

        typeRepo.save(mens);

        ItemType electronic = new ItemType();
        electronic.setName("electronics");
        typeRepo.save(electronic);

        Item item1 = new Item();
        item1.setTitle("Fjallraven - Foldsack No. 1 Backpack, Fits 15 Laptops");
        item1.setPrice(109.95);
        item1.setDescription("Your perfect pack for everyday use and walks in the forest. Stash your laptop " +
                "(up to 15 inches) in the padded sleeve, your everyday");
        item1.setCategory(mens);
        item1.setImage("https://fakestoreapi.com/img/81fPKd-2AYL._AC_SL1500_.jpg");

        itemRepo.save(item1);



        Item item2 = new Item();
        item2.setTitle("Mens Casual Premium Slim Fit T-Shirts");
        item2.setPrice(22.3);
        item2.setDescription("Slim-fitting style, contrast raglan long sleeve, three-button henley placket, Shirt");
        item2.setCategory(mens);
        item2.setImage("https://fakestoreapi.com/img/71-3HjGNDUL._AC_SY879._SX._UX._SY._UY_.jpg");

        itemRepo.save(item2);

        Item item3 = new Item();
        item3.setTitle("WD 2TB Elements Portable External Hard Drive - USB 3.0");
        item3.setPrice(64.0);
        item3.setDescription("USB 3.0 and USB 2.0 Compatibility Fast data transfers " +
                "Improve PC Performance High Capacity; Compatibility Formatted NTFS for Windows 10, Windows " +
                "8.1, Windows 7; Reformatting may be required for other operating systems");
        item3.setCategory(electronic);
        item3.setImage("https  ://fakestoreapi.com/img/61IBBVJvSDL._AC_SY879_.jpg");
        itemRepo.save(item3);
    }

}
