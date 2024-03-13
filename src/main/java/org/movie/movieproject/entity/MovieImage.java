package org.movie.movieproject.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
// movie필드는 제외한다는 exclude 명령어
@ToString(exclude="movie")
@SequenceGenerator(name="myMovieImgSeq", sequenceName = "movieimg_seq", initialValue = 1, allocationSize = 1)

public class MovieImage {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "myMovieImgSeq")
    private Long inum;
    private String uuid;
    private String imgName;
    private String path;
    @ManyToOne(fetch=FetchType.LAZY)
    private Movie movie;
}
