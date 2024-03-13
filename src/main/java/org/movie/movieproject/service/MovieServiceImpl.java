package org.movie.movieproject.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.movie.movieproject.dto.MovieDTO;
import org.movie.movieproject.dto.PageRequestDTO;
import org.movie.movieproject.dto.PageResultDTO;
import org.movie.movieproject.entity.Movie;
import org.movie.movieproject.entity.MovieImage;
import org.movie.movieproject.repository.MovieImageRepository;
import org.movie.movieproject.repository.MovieRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.net.FileNameMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Service
@Log4j2
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {
    private final MovieRepository movieRepository;
    private final MovieImageRepository movieImageRepository;

    @Override
    public Long register(MovieDTO movieDTO) {
        Map<String, Object> entityMap = dtoToEntity(movieDTO);
        // Object로 리턴하기에 Movie 객체로 강제캐스팅 진행
        Movie movie = (Movie) entityMap.get("movie");
        movieRepository.save(movie);
        List<MovieImage> movieImageList = (List<MovieImage>) entityMap.get("imgList");
        movieImageList.forEach(movieImage -> {
            movieImageRepository.save(movieImage);
        });
        return movie.getMno();
    }

    @Override
    public PageResultDTO<MovieDTO, Object[]> getList(PageRequestDTO pageRequestDTO) {

        Pageable pageable = pageRequestDTO.getPageable(Sort.by("mno").descending());
        Page<Object[]> result = movieRepository.getListPage3(pageable);
        Function<Object[], MovieDTO> fn = (arr -> entityToDTO(
                (Movie) arr[0],
                (List<MovieImage>) (Arrays.asList((MovieImage)arr[1])),
        (Double) arr[2], (Long) arr[3])
        );
        return new PageResultDTO<>(result, fn);
    }

    @Override
    public MovieDTO getMovie(Long mno) {
        List<Object[]> result = movieRepository.getMovieWithAll2(mno);
        // List의 0번째 index는 movie객체로 이중배열 형태로 값을 받아와 할당할 수 있다.
        Movie movie = (Movie) result.get(0)[0];
        // List의 0번째 index는 movieImage객체로 값이 여러개이므로 List로 값을 받아온다
        List<MovieImage> movieImages = new ArrayList<>();
        result.forEach(arr -> {
            MovieImage movieImage = (MovieImage) arr[1];
            movieImages.add(movieImage);
        });
        Double avg = (Double) result.get(0)[2];
        Long reviewCnt = (Long) result.get(0)[3];
        return entityToDTO(movie, movieImages, avg, reviewCnt);
    }
}
