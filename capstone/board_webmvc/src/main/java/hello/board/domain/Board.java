package hello.board.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


//domain = entity들의 집합
//erd에서 설계한 데이터들을 구현한것(id,title,content)
//그중 1:다,외래키 관계들을 매핑하고 정리해줌
//db에 저장된다면(->repository의 역할) 어떤 형태로 저장될것인지를 정해줌

//엔티티 매핑
@Entity
//게터
@Getter
//매개변수 없는 기본 생성자 / protect 접근권한 / 생성자 하나이므로 자동 주입
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board {

    //주 키 설정
    @Id
    //기본키 생성 디비에 위임
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //열 이름 설정
    @Column(name = "board_id")
    private Long id;

    private String title;

    //text타입
    @Column(columnDefinition = "TEXT")
    private String content;

    //다대일 관계 설정
    //LAZY는 실제로 정보를 사용하기 전까지 데이터를 로드하지않음
    @ManyToOne(fetch = FetchType.LAZY)
    //왜래키 매핑
    @JoinColumn(name = "user_id")
    private User user;

    private LocalDateTime registerDate;

    //생성자에 가까운데, 디자인 패턴이라고 보면됨 -> builder().user("john").title("글1") 이런식으로
    @Builder
    public Board(String title, String content, User user, LocalDateTime registerDate) {
        this.title = title;
        this.content = content;
        this.user = user;
        this.registerDate = registerDate;
    }

    //setter로 하나하나 수정하기보다 builder와 메서드를 사용

    //==생성 메서드==//
    public static Board createBoard(String title, String content, User user) {
        return Board.builder()
                .title(title).content(content).user(user)
                .registerDate(LocalDateTime.now())
                .build();
    }

    //==비즈니스 메서드==//
    //board 업데이트하는 메서드
    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
