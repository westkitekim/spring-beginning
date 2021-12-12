package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {
    Member save(Member member);
    //Optional : find 메서드로 값을 가져올 때 null일 수 있기 때문에
    //           null을 그대로 반환하지 않고 Optional로 감싸서 반환한다(java8 기능)
    Optional<Member> findById(Long id);
    Optional<Member> findByName(String name);
    List<Member> findAll();
}
