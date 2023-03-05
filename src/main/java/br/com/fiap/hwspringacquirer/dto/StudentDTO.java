package br.com.fiap.hwspringacquirer.dto;

import java.time.LocalDate;

public record StudentDTO (
        Long id,
        String name,
        String email,
        LocalDate birthDate,
        String course,
        String status
){
}
