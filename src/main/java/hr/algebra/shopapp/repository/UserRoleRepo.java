package hr.algebra.shopapp.repository;

import hr.algebra.shopapp.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepo extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
