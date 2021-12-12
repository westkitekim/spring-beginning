package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

//Interface가 Interface를 구현 시에는 extends라고 함
public interface SpringDataJpaMemberRepository extends JpaRepository<Member, Long>, MemberRepository {
    //구현 안 해도됨
    //SpringDataJpaRepository가 JpaRepository를 받고 있으면 구현체 자동 생성
    //=> SpringBean에 자동으로 등록

    //JPQL : select m from Member m where m.name = ?
    @Override
    Optional<Member> findByName(String name);
}
