package br.com.cinescore.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "directors")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DirectorModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String bio;
}
