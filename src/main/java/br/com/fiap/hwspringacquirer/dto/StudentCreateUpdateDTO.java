package br.com.fiap.hwspringacquirer.dto;

import java.time.LocalDate;

public record StudentCreateUpdateDTO(
        String name,
        String email,
        LocalDate birthDate,
        String course
){
}
