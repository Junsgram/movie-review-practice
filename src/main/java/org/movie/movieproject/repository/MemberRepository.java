package org.movie.movieproject.repository;

import org.movie.movieproject.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
