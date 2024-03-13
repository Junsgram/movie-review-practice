package org.movie.movieproject.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MovieDTO {
    private Long mno;
    private String title;

    // Builder가 적용된 클래스의 빌더 메소드를 생성할 때 특정 필드의 기본 값 설정
    // Builder Method가 호출될 때 해당 필드가 null이면 기본값으로 빈 객체 ArrayList를 할당하도록 지정
    @Builder.Default
    private List<MovieImageDTO> imageDTOList = new ArrayList<>();
    // 평점
    private double avg;
    // 리뷰의 수
    private int reviewCnt;
    private LocalDateTime regDate;

}
