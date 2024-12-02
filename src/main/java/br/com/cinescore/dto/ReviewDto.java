package br.com.cinescore.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDto {
    private Long id;
    private Integer rating;
    private String comment;
    private UserDto user;
    private String username;
    private MovieDto movie;
}
