package org.movie.movieproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class MovieProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(MovieProjectApplication.class, args);
    }

}
