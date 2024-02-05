package hr.algebra.shopapp.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Cart {
    private List<SelectedItem> items;

    public Cart() {
        items = new ArrayList<>();
    }

    public void addItem(Item item, int quantity) {
        if (items.stream().anyMatch(i -> i.getItem().getId().equals(item.getId()))) {
            int currentQuantity = items.stream().filter(i -> i.getItem().getId().equals(item.getId())).findFirst().orElseThrow().getQuantity();
            updateQuantity(item, currentQuantity + quantity);
        } else {
            items.add(SelectedItem.builder().item(item).quantity(quantity).build());
        }
    }

    public void removeItem(Item item) {
        items.removeIf(i -> i.getItem().getId().equals(item.getId()));
    }

    public void updateQuantity(Item item, int quantity) {
        if (quantity <= 0) {
            removeItem(item);
        } else if (items.stream().anyMatch(i -> i.getItem().getId().equals(item.getId()))) {
            items.removeIf(i -> i.getItem().getId().equals(item.getId()));
            items.add(SelectedItem.builder().item(item).quantity(quantity).build());
        }
    }

    public List<SelectedItem> getItems() {
        return items;
    }

    public double getTotal() {
        double total = 0;
        for (var item : items) {
            Item item1 = item.getItem();
            int quantity = item.getQuantity();

            double itemTotal = (item1.getPrice() * quantity);

            total += itemTotal;
        }
        return total;
    }

    public void clearCart() {
        items.clear();
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public void checkout() {
        // Logic to process the purchase, create an order, update inventory, etc.
        // This method can be further implemented based on your requirements.
        System.out.println("Checkout completed.");
    }
}
