package org.movie.movieproject.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"movie","member"})
@SequenceGenerator(name = "myReviewSeq", sequenceName = "review_seq", initialValue = 1, allocationSize = 1)
public class Review extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "myReviewSeq")
    private Long reviewnum;
    private String text;
    private int grade;
    @ManyToOne(fetch = FetchType.LAZY)
    private Movie movie;
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    public void changeGrade(int grade) {
        this.grade = grade;
    }
    public void changeText(String text) {
        this.text = text;
    }
}
