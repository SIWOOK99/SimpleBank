package hello.board.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private LocalDateTime registerDate;

    @Builder
    public Board(String title, String content, User user, LocalDateTime registerDate) {
        this.title = title;
        this.content = content;
        this.user = user;
        this.registerDate = registerDate;
    }

    //==생성 메서드==//
    public static Board createBoard(String title, String content, User user) {
        return Board.builder()
                .title(title).content(content).user(user)
                .registerDate(LocalDateTime.now())
                .build();
    }

    //==비즈니스 메서드==//
    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
