package br.com.cinescore.dto;

import br.com.cinescore.model.DirectorModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieDto {
    private Long id;
    private DirectorModel director;
    private String title;
    private Integer rating;
    private Integer releaseYear;
    private String genre;
    private Integer durationMinutes;
    private String image;
}
