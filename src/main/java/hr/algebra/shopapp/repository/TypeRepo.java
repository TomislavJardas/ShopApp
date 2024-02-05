package hr.algebra.shopapp.repository;

import hr.algebra.shopapp.model.ItemType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeRepo extends JpaRepository<ItemType, Long> {
}
