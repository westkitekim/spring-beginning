package hello.hellospring.repository;
//test는 클래스 단위, test\\java\\hello.hellospring에서 전체적으로 test가능
//본 클래스는 spring과는 관련없는 test. (순수한 java 코드)
import hello.hellospring.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

//굳이 public으로 하지 않아도 된다(다를 클래스를 가져와 사용할 것이 아니기 때문)
class MemoryMemberRepositoryTest {

    MemoryMemberRepository repository = new MemoryMemberRepository();

    //Test가 끝날 때마다 리포지토리를 깔끔하게 지워주는 코드를 필요
    @AfterEach //메서드 실행 끝날때마다 동작되는 코드
    public void afterEach() {
        repository.clearStore();
    }


    //import org.junit.jupiter.api.Test import 한다
    //Test Run : 코드라인 쪽에 초록색 실행버튼 클릭
    //Ctrl + Shift + Enter : 문장 자동완성
    @Test
    public void save() {
        Member member = new Member();
        member.setName("spring");
        //save 하면 시스템에서 자동적으로 id 저장
        repository.save(member);
        //Optional에서 값을 꺼낼 때는 get()으로 꺼낼 수 있다.(좋은 방법은 아님)
        Member result = repository.findById(member.getId()).get();
        //방법3.
        //assertj.core.api
        //static import - 우클릭 show context actions -> Assertions 없앨 수 있음
        assertThat(member).isEqualTo(result);

        //방법2.
        //junit.jupiter.api
        //Assertions.assertEquals(result, member);
        //검증 : new에서 저장한 것과 DB에서 꺼낸 것과 동일하면 참
        //sout 입력 후 Tab 키 -> 자동완성

        //방법 1.
        //이 방법으로도 test할 수 있지만 result = true 만 출력되어 데이터 값을 확인 할 수 없음
        //System.out.println("result = " + (result == member));
    }
    @Test
    public void findByName() {
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);
        //get()을 사용하면 Optional을 한 번 까서 사용가능(즉, 내부에 있는 객체로 반환)
        Member result = repository.findByName("spring2").get();

        assertThat(result).isEqualTo(member2);

    }

    @Test
    public void findAll() {
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        List<Member> result = repository.findAll();

        assertThat(result.size()).isEqualTo(2);
    }

}
