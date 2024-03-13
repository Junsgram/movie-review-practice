package org.movie.movieproject.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Stack;

@Entity
@Table(name="m_member")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SequenceGenerator(name="myMMemseq", sequenceName = "mmem_seq", initialValue = 1, allocationSize = 1)
public class Member extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "myMMemseq")
    private Long mid;
    private String email;
    private String pw;
    private String nickname;
}
