package hr.algebra.shopapp.repository;


import hr.algebra.shopapp.model.SelectedItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepo extends JpaRepository<SelectedItem, Long> {
    List<SelectedItem> findAllByOrderId(Long orderId);
}
