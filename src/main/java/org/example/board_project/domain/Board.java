package org.example.board_project.domain;

import lombok.*;
import org.springframework.data.annotation.Id;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
//@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Board {
    @Id
    private Long id;
    private String name;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String password;

    public Board() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
}
