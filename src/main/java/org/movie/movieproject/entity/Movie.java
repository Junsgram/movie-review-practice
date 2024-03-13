package org.movie.movieproject.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@SequenceGenerator(name ="movieSequence", sequenceName = "movie_seq", initialValue = 1, allocationSize = 1)
public class Movie extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "movieSequence")
    Long mno;

    @Column(length = 30, nullable = false)
    private String title;
}
