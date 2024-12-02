package br.com.cinescore.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DirectorDto {
    private Long id;
    private String name;
    private String bio;
}
