package br.com.fiap.hwspringacquirer.service;

import br.com.fiap.hwspringacquirer.dto.StudentCreateUpdateDTO;
import br.com.fiap.hwspringacquirer.dto.StudentDTO;
import br.com.fiap.hwspringacquirer.dto.StudentSimpleDTO;
import br.com.fiap.hwspringacquirer.dto.StudentStatusDTO;

import java.util.List;

public interface StudentService {

    List<StudentSimpleDTO> listAll(String course);
    StudentDTO get(Long id);
    StudentDTO create(StudentCreateUpdateDTO studentCreateUpdateDTO);
    StudentDTO update(Long id, StudentCreateUpdateDTO studentCreateUpdateDTO);
    StudentDTO updateByStatus(Long id, StudentStatusDTO studentStatusDTO);
    void delete(Long id);
}
