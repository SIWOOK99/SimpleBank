package hello.board.repository;

import hello.board.domain.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

//repository = Data Access Object
//실제 디비에 사용할 로직들을 구현함 -> 디비에 저장 save,디비에서 데이터찾기 findOne,findAll 등등
//실제 쿼리를 쓰는경우도있고(jdbc) 이미 만들어진 api를 사용해서(jpa) 간단하게 사용할수도있음


//repository 빈으로 등록
@Repository
public class UserRepository {

    //entity manager 주입
    //말그대로 entity들을 디비에 저장하고 관리해야하기 때문에 entity manager
    //실제로는 바로 디비가 아닌 영속성컨텍스트에 중간 저장
    @PersistenceContext
    private EntityManager em;

    public void save(User user) {
        //영속성 컨텍스트에 user를 저장
        em.persist(user);
    }

    public Optional<User> findOne(Long id) {
        //영속성 컨텍스트에 user class에서 id로 찾기
        User user = em.find(User.class, id);
        return Optional.ofNullable(user);
    }

    public List<User> findAll() {
        //직접 쿼리를 만들어서 list로 반환
        return em.createQuery("select u from User u", User.class)
                .getResultList();
    }

    public Optional<User> findByLoginId(String loginId) {
        List<User> result = em.createQuery("select u from User u where u.loginId = :loginId", User.class)
                .setParameter("loginId", loginId)
                .getResultList();
        return result.stream().findAny();
    }
}
