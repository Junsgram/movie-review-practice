package org.movie.movieproject.service;

import org.movie.movieproject.dto.MovieDTO;
import org.movie.movieproject.dto.MovieImageDTO;
import org.movie.movieproject.dto.PageRequestDTO;
import org.movie.movieproject.dto.PageResultDTO;
import org.movie.movieproject.entity.Movie;
import org.movie.movieproject.entity.MovieImage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public interface MovieService {
    // movie register
    Long register(MovieDTO movieDTO);
    // movie list
    PageResultDTO<MovieDTO, Object[]> getList(PageRequestDTO pageRequestDTO);
    // search
    MovieDTO getMovie(Long mno);

    // dto -> entity로 mapping
    // MovieDTO객체와, MovieImageDTO를 Entity로 변환하기 위해서 Map으로 리턴하여 값을 할당 및 리턴할 예정
    default Map<String, Object> dtoToEntity(MovieDTO movieDTO) {
        Map<String, Object> entityMap = new HashMap<>();
        Movie movie = Movie.builder()
                .mno(movieDTO.getMno())
                .title(movieDTO.getTitle())
                .build();
        entityMap.put("movie",movie);

        //MovieImageDTO -> entity
        List<MovieImageDTO> imageDTOList = movieDTO.getImageDTOList();

        // imageDTOList가 null이 아니고 imageDTOList의 size가 0보다 클 때
        if(imageDTOList != null && imageDTOList.size() >0) {
            List<MovieImage> movieImgList = imageDTOList.stream()
                    .map(movieImageDTO -> {
                        MovieImage movieImage = MovieImage.builder()
                                .imgName(movieImageDTO.getImgName())
                                .uuid(movieImageDTO.getUuid())
                                .path(movieImageDTO.getPath())
                                .movie(movie)
                                .build();
                        return movieImage;
                    }).collect(Collectors.toList());
            entityMap.put("imgList", movieImgList);
        };
        return entityMap;
    }

    // 조회메소드를 사용하면 entity객체를 반환하고 그 그값(entity) -> dto 객체로 반환하기 위한 메소드
    // JPA를 통해서 조회된 엔티티 객체들과 Double, Long등의 값을 MovieDTO로 변환
    default MovieDTO entityToDTO(Movie movie, List<MovieImage> movieImages, Double avg, Long reviewCnt) {
        MovieDTO movieDTO = MovieDTO.builder()
                .mno(movie.getMno())
                .title(movie.getTitle())
                .regDate(movie.getRegDate())
                .build();

        List<MovieImageDTO> movieImageDTOList = movieImages.stream()
                .map(movieImage -> {
                    return MovieImageDTO.builder()
                            .path(movieImage.getPath())
                            .uuid(movieImage.getUuid())
                            .imgName(movieImage.getImgName())
                            .build();
                }).collect(Collectors.toList());
        movieDTO.setImageDTOList(movieImageDTOList);
        movieDTO.setAvg(avg);
        movieDTO.setReviewCnt(reviewCnt.intValue());
        return movieDTO;
    }
}
