package hr.algebra.shopapp.Service;


import hr.algebra.shopapp.model.Item;
import hr.algebra.shopapp.repository.ItemRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {

    @Autowired
    private ItemRepo itemRepository;

    public List<Item> findAll() {
        return itemRepository.findAll();
    }
}
