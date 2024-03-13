package org.movie.movieproject.repository;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.movie.movieproject.entity.Member;
import org.movie.movieproject.entity.Movie;
import org.movie.movieproject.entity.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

import java.lang.management.MonitorInfo;
import java.util.List;
import java.util.stream.IntStream;

@SpringBootTest
public class ReviewRepositoryTests {
    @Autowired
    ReviewRepository reviewRepository;

    @DisplayName("리뷰 등록 테스트")
    @Test
    public void insertMovieReview() {
        IntStream.rangeClosed(1,200).forEach(i->{
            // 영화 번호 랜덤 출력
            Long mno = (long) (Math.random()*100)+1;
            // 리뷰어 번호 랜덤 출력
            Long mid = (long) (Math.random()*100)+1;

            Member member = Member.builder().mid(mid).build();
            Movie movie = Movie.builder().mno(mno).build();
            Review movieReview = Review.builder()
                    .member(member)
                    .movie(movie)
                    .grade((int) (Math.random()*5)+1)
                    .text("이 영화에 대해 느낀점 " + i)
                    .build();
            reviewRepository.save(movieReview);
        });
    }

    @DisplayName("특정영화의 모든 리뷰 및 회원의 닉네임")
    @Transactional
    @Test
    public void testGetMovieReview() {
        Movie movie = Movie.builder().mno(99L).build();
        List<Review> result = reviewRepository.findByMovie(movie);
        for(Review r : result ) {
            System.out.println(r);
            System.out.println(r.getMember().getEmail());
        }
    }
}
