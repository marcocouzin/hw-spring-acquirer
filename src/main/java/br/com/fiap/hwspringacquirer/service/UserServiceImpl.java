package br.com.fiap.hwspringacquirer.service;

import br.com.fiap.hwspringacquirer.dto.AuthDTO;
import br.com.fiap.hwspringacquirer.dto.CreateUserDTO;
import br.com.fiap.hwspringacquirer.dto.JwtDTO;
import br.com.fiap.hwspringacquirer.dto.UserDTO;
import br.com.fiap.hwspringacquirer.entity.UserEntity;
import br.com.fiap.hwspringacquirer.repository.UserRepository;
import br.com.fiap.hwspringacquirer.security.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtTokenUtil;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(AuthenticationManager authenticationManager,
                           JwtUtil jwtTokenUtil,
                           UserRepository userRepository,
                           PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public JwtDTO login(AuthDTO authDTO) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    authDTO.username(),
                    authDTO.password()
            ));
        } catch (DisabledException disabledException) {
            throw new Exception("user.disabled", disabledException);
        } catch (BadCredentialsException badCredentialsException) {
            throw new Exception("bad.credentials", badCredentialsException);
        }

        String token = jwtTokenUtil.generateToken(authDTO.username());

        return new JwtDTO(token);
    }

    @Override
    public UserDTO createUser(CreateUserDTO createUserDTO) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUserName(createUserDTO.username());
        userEntity.setPassword(passwordEncoder.encode(createUserDTO.password()));

        UserEntity savedUserEntity = userRepository.save(userEntity);

        return new UserDTO(
                savedUserEntity.getId(),
                savedUserEntity.getUserName()
        );
    }

}
