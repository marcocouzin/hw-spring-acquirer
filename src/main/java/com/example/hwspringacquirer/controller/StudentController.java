package com.example.hwspringacquirer.controller;

import com.example.hwspringacquirer.dto.StudentCreateUpdateDTO;
import com.example.hwspringacquirer.dto.StudentDTO;
import com.example.hwspringacquirer.dto.StudentSimpleDTO;
import com.example.hwspringacquirer.dto.StudentStatusDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController()
@RequestMapping("students")
public class StudentController {

    private final List<StudentDTO> studentDTOS = new ArrayList<>();

    StudentController(){
        this.studentDTOS.addAll(
                Arrays.asList(
                    new StudentDTO(1L, "Marco Couzin", "mcouzin@gmail.com", LocalDate.of(1978, 6, 16), "1SCJR", "Enabled"),
                    new StudentDTO(2L, "Aline de Paula", "aline@gmail.com", LocalDate.of(1985, 7, 11), "1SCJR", "Enabled"),
                    new StudentDTO(3L, "Bruna Couzin", "bruna@gmail.com", LocalDate.of(2010, 11, 1), "2SCJR", "Enabled"),
                    new StudentDTO(4L, "Helena Couzin", "helena@gmail.com", LocalDate.of(2020, 4, 1), "3SCJR", "Enabled")
                )
        );
    }

    @GetMapping
    public List<StudentSimpleDTO> listAll(@RequestParam(required = false) String course) {
        return this.studentDTOS.stream()
                .filter(studentDTOS -> course==null || studentDTOS.course().equals(course))
                .map(studentDTOS -> new StudentSimpleDTO(studentDTOS.id(), studentDTOS.name(), studentDTOS.course()))
                .collect(Collectors.toList());
    }

    @GetMapping(value = "{id}")
    public StudentDTO findById(@PathVariable Long id){
        return this.studentDTOS.stream()
                .filter(studentDTOs -> studentDTOs.id().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public StudentDTO create(@RequestBody StudentCreateUpdateDTO studentCreateUpdateDTO ) {
        StudentDTO newStudent = new StudentDTO(this.studentDTOS.size() + 1L, studentCreateUpdateDTO.name(), studentCreateUpdateDTO.email(), studentCreateUpdateDTO.birthDate(), studentCreateUpdateDTO.course(), "Enabled");
        this.studentDTOS.add(newStudent);
        return newStudent;
    }

    @PutMapping({"{id}"})
    public StudentDTO update (@PathVariable Long id,
                              @RequestBody StudentCreateUpdateDTO studentCreateUpdateDTO) {
        StudentDTO studentDTO = findById(id);
        this.studentDTOS.remove(studentDTO);

        StudentDTO updateStudent = new StudentDTO(
                studentDTO.id(),
                studentCreateUpdateDTO.name(),
                studentCreateUpdateDTO.email(),
                studentCreateUpdateDTO.birthDate(),
                studentCreateUpdateDTO.course(),
                studentDTO.status()
        );

        this.studentDTOS.add(updateStudent);
        return updateStudent ;
    }


    @PatchMapping({"{id}"})
    public StudentDTO updateStatus (@PathVariable Long id,
                              @RequestBody StudentStatusDTO studentStatusDTO) {
        StudentDTO studentDTO = findById(id);

        StudentDTO updateStatusStudent = new StudentDTO(
                studentDTO.id(),
                studentDTO.name(),
                studentDTO.email(),
                studentDTO.birthDate(),
                studentDTO.course(),
                studentStatusDTO.status()
        );

        this.studentDTOS.add(this.studentDTOS.indexOf(studentDTO), updateStatusStudent);
        return updateStatusStudent ;
    }

    @DeleteMapping({"{id}"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id){
        StudentDTO studentDTO = findById(id);
        this.studentDTOS.remove(studentDTO);
    }
}

// Parei em 1:01:18
