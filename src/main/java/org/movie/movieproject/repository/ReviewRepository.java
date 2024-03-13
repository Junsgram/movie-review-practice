package org.movie.movieproject.repository;

import jakarta.transaction.Transactional;
import org.movie.movieproject.entity.Member;
import org.movie.movieproject.entity.Movie;
import org.movie.movieproject.entity.Review;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.lang.reflect.Type;
import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    // 특정영화의 모든 리뷰 및 회원의 닉네임
    // Review 클래스에 Member객체에 Fetch = FetchType.Lazy로 지연로딩을 지정했기에 즉시로딩으로 변경하여 값을 가져와야한다.
    // @EntityGraph(attributePaths = {"member"}, type = EntityGraph.EntityGraphType.FETCH) // 엔티티의  특정한 속성을 같이 로딩을 진행한다.
    List<Review> findByMovie(Movie movie);

    // 삭제
    void deleteByMember(Member member);
}
