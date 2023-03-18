package br.com.fiap.hwspringacquirer.security;

import br.com.fiap.hwspringacquirer.entity.UserEntity;
import br.com.fiap.hwspringacquirer.repository.UserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public JwtUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findFirstByUserName(username);
        if (userEntity == null){
            throw new UsernameNotFoundException("username.not.found");
        }

        return new User(
                userEntity.getUserName(),
                userEntity.getPassword(),
                new ArrayList<>());
    }
}
