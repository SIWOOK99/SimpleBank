package hello.board.service;

import hello.board.domain.User;
import hello.board.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

//service = domain과 repository를 통해 핵심 로직을 구현함
//service에 회원가입을 구현할때의 단계를 적어보면

/* 단계별 설명
1. user domain(login id,password,name,age) 객체를 받아와서
2. 내부로직 = id가 중복되는지 검사가 필요
3. 이후 database에 저장할때 userRepository의 save를 사용하여 저장
*/

@Service
@Transactional(readOnly = true)
//userRepository를 매개변수로 받아서 생성자만듦 = 생성자 주입
@RequiredArgsConstructor
public class UserService {

    //@repository로 넣은 빈을 등록
    private final UserRepository userRepository;

    /**
     * 회원 가입
     */
    @Transactional
    public Long join(User user) {
        validateDuplicateLoginId(user); //중복 로그인 아이디 검증
        userRepository.save(user);
        return user.getId();
    }

    private void validateDuplicateLoginId(User user) {
        userRepository.findByLoginId(user.getLoginId())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 아이디입니다.");
                });
    }

    /**
     * 회원 전체 조회
     */
    public List<User> findUsers() {
        return userRepository.findAll();
    }

    /**
     * 회원 단건 조회
     */
    public Optional<User> findOne(Long id) {
        return userRepository.findOne(id);
    }
}
