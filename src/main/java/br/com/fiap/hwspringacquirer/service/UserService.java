package br.com.fiap.hwspringacquirer.service;

import br.com.fiap.hwspringacquirer.dto.AuthDTO;
import br.com.fiap.hwspringacquirer.dto.CreateUserDTO;
import br.com.fiap.hwspringacquirer.dto.JwtDTO;
import br.com.fiap.hwspringacquirer.dto.UserDTO;

public interface UserService {
    JwtDTO login(AuthDTO authDTO) throws Exception;
    UserDTO createUser(CreateUserDTO createUserDTO);
}
