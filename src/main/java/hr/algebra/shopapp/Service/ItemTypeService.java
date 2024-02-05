package hr.algebra.shopapp.Service;


import hr.algebra.shopapp.model.ItemType;
import hr.algebra.shopapp.repository.TypeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemTypeService {

    @Autowired
    private TypeRepo itemTypeRepository;

    public List<ItemType> findAll() {
        return itemTypeRepository.findAll();
    }

    public ItemType findById(Long id) {
        Optional<ItemType> itemType = itemTypeRepository.findById(id);
        return itemType.orElse(null);
    }

    public void save(ItemType itemType) {
        itemTypeRepository.save(itemType);
    }

    public void deleteById(Long id) {
        itemTypeRepository.deleteById(id);
    }
}

