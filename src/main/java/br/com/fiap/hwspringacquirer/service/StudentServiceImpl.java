package br.com.fiap.hwspringacquirer.service;

import br.com.fiap.hwspringacquirer.dto.StudentCreateUpdateDTO;
import br.com.fiap.hwspringacquirer.dto.StudentDTO;
import br.com.fiap.hwspringacquirer.dto.StudentSimpleDTO;
import br.com.fiap.hwspringacquirer.dto.StudentStatusDTO;
import br.com.fiap.hwspringacquirer.entity.StudentEntity;
import br.com.fiap.hwspringacquirer.repository.StudentRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final ObjectMapper objectMapper;

    public StudentServiceImpl(StudentRepository studentRepository, ObjectMapper objectMapper) {
        this.studentRepository = studentRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public List<StudentSimpleDTO> listAll(String course) {
        List<StudentEntity> studentEntity;

        if (course != null) {
            studentEntity = this.studentRepository.findAllByCourse(course);
        } else {
            studentEntity = this.studentRepository.findAll();
        }

        return studentEntity
                .stream()
                .map(StudentEntity -> new StudentSimpleDTO(
                        StudentEntity.getId(),
                        StudentEntity.getName(),
                        StudentEntity.getCourse()))
                .collect(Collectors.toList());
    }

    @Override
    public StudentDTO get(Long id) {
        StudentEntity studentEntity = this.getStudentEntity(id);
        return this.objectMapper.convertValue(studentEntity, StudentDTO.class);
    }


    @Override
    public StudentDTO create(StudentCreateUpdateDTO studentCreateUpdateDTO) {
        StudentEntity studentEntity = new StudentEntity();

        studentEntity.setName(studentCreateUpdateDTO.name());
        studentEntity.setCourse(studentCreateUpdateDTO.course());
        studentEntity.setBirthDate(studentCreateUpdateDTO.birthDate());
        studentEntity.setEmail(studentCreateUpdateDTO.email());
        studentEntity.setStatus("enabled");

        return this.savedStudentEntityAndGetStudentDTO(studentEntity);
    }

    @Override
    public StudentDTO update(Long id, StudentCreateUpdateDTO studentCreateUpdateDTO) {
        StudentEntity studentEntity = this.getStudentEntity(id);

        studentEntity.setName(studentCreateUpdateDTO.name());
        studentEntity.setEmail(studentCreateUpdateDTO.email());
        studentEntity.setBirthDate(studentCreateUpdateDTO.birthDate());
        studentEntity.setCourse(studentCreateUpdateDTO.course());

        return this.savedStudentEntityAndGetStudentDTO(studentEntity);
    }


    @Override
    public StudentDTO updateByStatus(Long id, StudentStatusDTO studentStatusDTO) {
        StudentEntity studentEntity = this.getStudentEntity(id);

        studentEntity.setStatus(studentStatusDTO.status() );

        return this.savedStudentEntityAndGetStudentDTO(studentEntity);
    }

    @Override
    public void delete(Long id) {
        StudentEntity studentEntity = this.getStudentEntity(id);
        this.studentRepository.delete(studentEntity);
    }

    private StudentEntity getStudentEntity(Long id) {
        return this.studentRepository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    private StudentDTO savedStudentEntityAndGetStudentDTO(StudentEntity studentEntity) {
        StudentEntity savedStudentEntity = this.studentRepository.save(studentEntity);
        return this.objectMapper.convertValue(savedStudentEntity, StudentDTO.class);
    }


}
