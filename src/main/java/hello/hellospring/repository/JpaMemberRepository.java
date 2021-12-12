package hello.hellospring.repository;

import hello.hellospring.domain.Member;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class JpaMemberRepository implements  MemberRepository {
    //JPA는 EntityManager라는 것으로 모두 동작됨
    //build.gradle에서 data-jpa 라이브러리를 추가하면 EntityManager 사용가능
    private final EntityManager em;

    public JpaMemberRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Member save(Member member) {
        em.persist(member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        Member member = em.find(Member.class, id);
        return Optional.ofNullable(member);
    }

    /**
     *
     * pk 기반이 아닌 구현체는 JPQL을 작성해야 한다(객체를 대상으로 한 query)
     * @param name
     * @return
     */
    @Override
    public Optional<Member> findByName(String name) {
        List<Member> result = em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
        return result.stream().findAny();
    }

    //jpql 쿼리 원어 사용 : 테이블 대상이 아닌 객체 대상으로 query 날리는 방식 => sql로 변형됨
    //m이라는 객체 자체를 select, Member를 m으로 alias
    @Override
    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class).getResultList();
    }
}
