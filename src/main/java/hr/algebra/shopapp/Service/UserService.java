package hr.algebra.shopapp.Service;


import hr.algebra.shopapp.dto.UserDto;
import hr.algebra.shopapp.model.Role;
import hr.algebra.shopapp.model.User;
import hr.algebra.shopapp.repository.LoginRepo;
import hr.algebra.shopapp.repository.UserRoleRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final LoginRepo loginRepo;
    private final UserRoleRepo userRoleRepo;
    private final PasswordEncoder passwordEncoder;

    public void saveUser(UserDto userDto) {
        User user = new User();
        user.setName(userDto.getFirstName());
        user.setEmail(userDto.getEmail());

        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        Role role = userRoleRepo.findByName("ROLE_USER");
        if(role == null){
            role = checkRoleExist();
        }
        user.setRoles(List.of(role));
        loginRepo.save(user);
    }

    public User findByEmail(String email) {
        return loginRepo.findByEmail(email);
    }

    public List<User> findAllUsers() {
        return loginRepo.findAll();
    }

    private Role checkRoleExist(){
        Role role = new Role();
        role.setName("ROLE_USER");
        return userRoleRepo.save(role);
    }
}
