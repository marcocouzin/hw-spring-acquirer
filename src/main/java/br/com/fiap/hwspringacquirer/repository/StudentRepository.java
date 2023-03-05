package br.com.fiap.hwspringacquirer.repository;

import br.com.fiap.hwspringacquirer.entity.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentRepository extends JpaRepository<StudentEntity, Long> {

    List<StudentEntity> findAllByCourse(String course);
}
