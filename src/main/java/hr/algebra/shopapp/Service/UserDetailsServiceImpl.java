package hr.algebra.shopapp.Service;

import hr.algebra.shopapp.model.User;
import hr.algebra.shopapp.repository.LoginRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private LoginRepo userRepository;
    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);
        if(user ==null){
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return user;
    }
}
