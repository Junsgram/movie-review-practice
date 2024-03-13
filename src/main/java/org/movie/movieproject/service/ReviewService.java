package org.movie.movieproject.service;

import org.movie.movieproject.dto.ReviewDTO;
import org.movie.movieproject.entity.Member;
import org.movie.movieproject.entity.Movie;
import org.movie.movieproject.entity.Review;

import java.util.List;

public interface ReviewService {
    // 특정 영화의 모든 리뷰를 조회
    List<ReviewDTO> getListOfMovie(Long mno);

    // 영화 리뷰 추가하기
    Long register(ReviewDTO reviewDTO);

    // 영화 리뷰 수정하기
    void modify(ReviewDTO reviewDTO);

    // 영화 리뷰 삭제하기
    void remove(Long reviewnum);

    // dto -> entity Mapping
    default Review dtoToEntity(ReviewDTO reviewDTO) {
        Review review = Review.builder()
                .reviewnum(reviewDTO.getReviewnum())
                .movie(Movie.builder().mno(reviewDTO.getMno()).build())
                .member(Member.builder().mid(reviewDTO.getMid()).build())
                .grade(reviewDTO.getGrade())
                .text(reviewDTO.getText())
                .build();
        return review;
    }
    // entity -> dto Mapping
    default ReviewDTO entityToDTO(Review review) {
        ReviewDTO reviewDTO = ReviewDTO.builder()
                .reviewnum(review.getReviewnum())
                .mno(review.getMovie().getMno())
                .mid(review.getMember().getMid())
                .nickname(review.getMember().getNickname())
                .email(review.getMember().getEmail())
                .grade(review.getGrade())
                .text(review.getText())
                .regDate(review.getRegDate())
                .modDate(review.getModDate())
                .build();
        return reviewDTO;
    }
}
