package org.movie.movieproject.repository;


import org.movie.movieproject.entity.Movie;
import org.movie.movieproject.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long> {
    // 영화이름(객체), 영화 평점, 리뷰 갯수 구하는 쿼리문
    // 목록 화면에 영화제목, 영화리뷰평점, 리뷰 개수 조회
    @Query("select m, avg(coalesce(r.grade,0)), count(distinct r) from Movie m " +
            " left outer join Review r on r.movie = m " +
            " group by m")
    Page<Object[]> getListPage(Pageable pageable);

    // 영화이름(객체), 영화 평점, 리뷰 갯수, 이미지를 추가한 쿼리문
    // 목록화면에 영화 제목, 이미지번호, 영화리뷰 평점, 리뷰개수 조회
    @Query("select m, max(mi.inum), avg(coalesce(r.grade,0)), count(distinct r) from Movie m " +
            " left outer join MovieImage mi on mi.movie = m " +
            " left outer join Review r on r.movie = m" +
            " group by m")
    Page<Object[]> getListPage2(Pageable pageable);

    // 영화객체, 영화이미지 객체, 영화 리뷰 평점, 리뷰 개수 조회
    // 위와 동일하지만 위는 사진 값만 받아오고, 이 쿼리문은 객체 전체를 받기 떄문에 따로 조회가 필요없다.
    @Query("select m, mi, avg(coalesce(r.grade,0)), count(distinct r) from Movie m" +
                  " left outer join MovieImage mi on mi.movie = m" +
            " and mi.inum = (select max(i2.inum) from MovieImage i2 where i2.movie = m group by i2.movie) " +
            " left outer join Review r on r.movie = m group by m, mi")
    Page<Object[]> getListPage3(Pageable pageable);

    // 영화 상세보기 페이지, mno에 해당 하는 특정영화 및 이미지 출력
    @Query("select m, mi from Movie m left outer join MovieImage mi on mi.movie = m " +
            " where m.mno = :mno ")
    List<Object[]> getMovieWithAll(@Param("mno") Long mno);

    // 영화와 이미지들 평점 리뷰 개수
    @Query("select m, mi, avg(coalesce(r.grade, 0)), count(distinct r) from Movie m left outer join" +
            " MovieImage mi on mi.movie = m left outer join Review r on r.movie = m " +
            " where m.mno = :mno group by m,mi")
    List<Object[]> getMovieWithAll2(@Param("mno") Long mno);


}
