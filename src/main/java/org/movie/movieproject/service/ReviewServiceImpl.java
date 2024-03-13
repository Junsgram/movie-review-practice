package org.movie.movieproject.service;

import lombok.RequiredArgsConstructor;
import org.movie.movieproject.dto.ReviewDTO;
import org.movie.movieproject.entity.Movie;
import org.movie.movieproject.entity.Review;
import org.movie.movieproject.repository.ReviewRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;

    @Override
    public List<ReviewDTO> getListOfMovie(Long mno) {
        Movie movie = Movie.builder().mno(mno).build();
        List<Review> result = reviewRepository.findByMovie(movie);
        // List에 담긴 Review를 ReviewDTO가 담긴 List로 변환
        return result.stream().map(review -> entityToDTO(review))
                .collect(Collectors.toList());
    }

    @Override
    public Long register(ReviewDTO reviewDTO) {
        Review review = dtoToEntity(reviewDTO);
        reviewRepository.save(review);
        return review.getReviewnum();
    }

    @Override
    public void modify(ReviewDTO reviewDTO) {
        // 수정할 레코드를 조회
        Optional<Review> result = reviewRepository.findById(reviewDTO.getReviewnum());
        if(result.isPresent()) {
            Review review = result.get();
            review.changeGrade(reviewDTO.getGrade());
            review.changeText(reviewDTO.getText());
            reviewRepository.save(review);
        }
    }

    @Override
    public void remove(Long reviewnum) {
        reviewRepository.deleteById(reviewnum);
    }
}
