package hr.algebra.shopapp.repository;


import hr.algebra.shopapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginRepo extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
