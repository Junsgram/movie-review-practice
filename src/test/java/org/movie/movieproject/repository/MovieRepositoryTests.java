package org.movie.movieproject.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.movie.movieproject.entity.Movie;
import org.movie.movieproject.entity.MovieImage;
import org.movie.movieproject.entity.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

@SpringBootTest
public class MovieRepositoryTests {
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private MovieImageRepository imageRepository;

    @Test
    public void insertMovie() {
        IntStream.rangeClosed(1,100).forEach(i->{
            Movie movie = Movie.builder()
                    .title("Movie...." + i)
                    .build();
            movieRepository.save(movie);

            int count = (int) (Math.random()*5)+1; // 1~5
            for (int j = 0; j < count; j++) {
                MovieImage image = MovieImage.builder()
                        .uuid(UUID.randomUUID().toString())
                        .movie(movie)
                        .imgName("test" + j + ".jpg").build();
                imageRepository.save(image);
            }
        });
    }

    @DisplayName("영화 페이징 테스트, 영화객체, 평점, 리뷰 갯수")
    @Test
    public void testListPage() {
        Pageable pageable = PageRequest.of(0,10, Sort.by("mno").descending());
        Page<Object[]> result = movieRepository.getListPage(pageable);
        for(Object[] arr : result.getContent()){
            System.out.println(Arrays.toString(arr));
        }
    }

    @DisplayName("이미지 1개만 가지고오는 리스트 테스트")
    @Test
    public void testListPage2() {
        Pageable pageable = PageRequest.of(0,10,Sort.by("mno").descending());
        Page<Object[]> result = movieRepository.getListPage2(pageable);
        for(Object[] arr : result) {
            System.out.println(Arrays.toString(arr));
        }

    }
    @DisplayName("영화객체, 영화이미지 객체, 영화 리뷰 평점, 리뷰 개수 조회")
    @Test
    public void testListPage3() {
        Pageable pageable = PageRequest.of(0,10,Sort.by("mno").descending());
        Page<Object[]> result = movieRepository.getListPage3(pageable);
        for(Object[] arr : result ) {
            System.out.println(Arrays.toString(arr));
        }
    }

    @DisplayName("영화 상세보기 테스트")
    @Test
    public void getMovieWithAll() {
        List<Object[]> result = movieRepository.getMovieWithAll(48L);
        for(Object[] arr : result)  {
            System.out.println(Arrays.toString(arr));
        }
    }

    @DisplayName("영화와 이미지들 평점 리뷰 개수")
    @Test
    public void getMovieWithAll2() {
        List<Object[]> result = movieRepository.getMovieWithAll2(48L);
        for(Object[] arr : result) {
            System.out.println(Arrays.toString(arr));
        }
    }


}
