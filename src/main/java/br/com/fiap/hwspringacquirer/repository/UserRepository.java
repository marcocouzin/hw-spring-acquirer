package br.com.fiap.hwspringacquirer.repository;

import br.com.fiap.hwspringacquirer.entity.UserEntity;
import org.h2.engine.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findFirstByUserName(String username);
}
