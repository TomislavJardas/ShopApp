package hr.algebra.shopapp.Service;


import hr.algebra.shopapp.model.SelectedItem;
import hr.algebra.shopapp.repository.CartRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartService {
    @Autowired
    private CartRepo cartRepo;

    public List<SelectedItem> getCartItems() {
        return cartRepo.findAll();
    }

    public void addItem(SelectedItem selectedItem) {

        Optional<SelectedItem> existingItem = cartRepo.findById(selectedItem.getId());
        if (existingItem.isPresent()) {

            SelectedItem itemToUpdate = existingItem.get();
            itemToUpdate.setQuantity(itemToUpdate.getQuantity() + selectedItem.getQuantity());
            cartRepo.save(itemToUpdate);
        } else {

            cartRepo.save(selectedItem);
        }
    }

    public void updateItem(SelectedItem selectedItem) {

        cartRepo.save(selectedItem);
    }

    public void removeItem(Long itemId) {
        cartRepo.deleteById(itemId);
    }

    public double getTotalPrice() {
        return cartRepo.findAll().stream()
                // Filter out any selected items with a null item
                .filter(item -> item.getItem() != null)
                // Calculate the total price
                .mapToDouble(item -> item.getItem().getPrice() * item.getQuantity())
                // Sum the prices
                .sum();
    }


    public void clearCart() {
        cartRepo.deleteAll();
    }
}
