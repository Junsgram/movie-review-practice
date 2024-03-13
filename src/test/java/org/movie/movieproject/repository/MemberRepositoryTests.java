package org.movie.movieproject.repository;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.movie.movieproject.entity.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

import java.util.stream.IntStream;

@SpringBootTest
public class MemberRepositoryTests {
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private ReviewRepository reviewRepository;

    @DisplayName("테이블 값 등록 테스트")
    @Test
    public void insertMember() {
        IntStream.rangeClosed(1,100).forEach(i->{
            Member member = Member.builder()
                    .email("r"+i+"@google.com")
                    .pw("1234")
                    .nickname("reviewer"+i+"님")
                    .build();
            memberRepository.save(member);
        });
    }

    @DisplayName("댓글 삭제 및 회원 삭제 ")
    @Test
    @Transactional
    @Commit
    public void testDeleteMember() {
        Member member = Member.builder().mid(99L).build();
        reviewRepository.deleteByMember(member);
        memberRepository.deleteById(member.getMid());
    }
}
