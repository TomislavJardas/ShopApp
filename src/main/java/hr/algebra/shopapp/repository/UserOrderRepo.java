package hr.algebra.shopapp.repository;

import hr.algebra.shopapp.model.UserOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserOrderRepo extends JpaRepository<UserOrder, Long> {
    List<UserOrder> findAllByUsername(String username);
}
