package hello.hellospring.repository;
//회원 리포지토리 클래스가 정상작동되는지 검증하기 위해 TestCase 작성한다
//코드를 코드로 검증한다.
import hello.hellospring.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.*;

public class MemoryMemberRepository implements MemberRepository{
    //실무에서는 동시성 문제가 있어서 공유되는 변수(저장되는 store 변수처럼)는 다른 것을 써야하지만 예제이므로 hashmap 사용
    //MemberService, MemberServiceTest에서 해당 현재클래스 인스턴스를 생성하기 때문에 static 으로 선언 필요
    private static Map<Long, Member> store = new HashMap<>();
    private static long sequence = 0L;//시퀀스 생성
    @Override
    public Member save(Member member) {
        //아래 코드를 수행하려면 결국, DB에서 새퀀스 값이 어떤 것이 생성되었는지 가져오는 역할을 해야한다.
        member.setId(++sequence);//db에 데이터 넣었을 때 시퀀스를 통해 자동적으로 값이 증가되도록 설정하였음
        store.put(member.getId(), member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        //Null인 경우를 대비하여 Optional.ofNullable 로 감싸서 반환(클라이언트에서 뭘 할 수 있다?)
        return Optional.ofNullable(store.get(id));
    }

    //findAny는 하나로도 찾는 것, 찾아지면 반환, 없으면 Optional에 넣어서 반환
    @Override
    public Optional<Member> findByName(String name) {
        //getName이 파라미터로 넘어온 name과 같은지 비교
        return store.values().stream()
                .filter(member -> member.getName().equals(name))
                .findAny();
    }

    //Map의 value값인 Member 객체가 리스트 형태로 반환
    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }

    //Test가 끝날 때마다 리포지토리를 깔끔하게 지워주는 코드를 필요
    public void clearStore() {
        store.clear();
    }
}
