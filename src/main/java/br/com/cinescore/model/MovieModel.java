package br.com.cinescore.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "movies")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MovieModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String title;

    @ManyToOne
    @JoinColumn(name = "director_id", nullable = false)
    private DirectorModel director;

    @Column(name = "release_year")
    private int releaseYear;

    @Column
    private String genre;

    @Column(name = "duration_minutes")
    private int durationMinutes;

    @Column
    private String image;
}
