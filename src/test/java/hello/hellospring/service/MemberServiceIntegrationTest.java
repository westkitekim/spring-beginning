package hello.hellospring.service;

/**
 * Spring 통합 테스트(테스트도 DB까지 연동해보기)
 * Spring에게 MemberService, MemoryMemberRepository 객체 달라고 해야함(DI)
 *
 * Test이므로(필요한 객체 주입받아서 test해보고 끝,
 * 즉, MemberServiceIntegrationTest객체를 다른 곳에서 사용하는 것이 아니기 때문에
 * 간편하게필드주입
 *
 * Test 전용 DB는 따로 구축 or Local PC에 있는 DB에서 TEST
 *
 * Test는 반복할 수 있어야 한다.
 * 방법 1. 이전의 BeforeEach, AfterEach,
 * 방법 2. @Transactional 어노테이션 : DB insert query 다 날리고 rollback 하여 Test 수행
 *
 */

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

//Ctrl + Shift + T 로 테스트 클래스 자동 생성, 똑같은 패키지도 생성한다
//Assertions는 assertj 패키지를 사용함, junit 아님

//test 실행 시 트랜잭션 먼저 실행, 테스트가 끝나면 rollback 하는 기능(넣었던 데이터 깜끔히 지워짐)
//다음 테스트를 또 실행할 수 있다는 뜻
@SpringBootTest
@Transactional
class MemberServiceIntegrationTest {
    //테스트는 영어권에서 일하는 것이 아니면 한글로 보통 많이 작성한다. 직관적,
    //빌드될 때 테스트 코드는 실제 코드에 포함되지 않는다

    //Test : 1. given, 2. when, 3. then 을 기억한다 !!
    //given : 이러한 상황이 주어짐, when : 이것을 실행했을 때, then : 결과가 ~ 것이 나와야 한다
    //Test는 정상수행 확인도 중요하지만 예외가 잘 수행되는 지도 중요!
    @Autowired MemberService memberService;//MemberService만 있으면 clear 안됨
    //new 로 자꾸 생성하면 내용물이 달라질 수 도 있는 등 위험성이 존재
    //즉, MemberServivce에서 만든 new MemoryMemberRepository()와
    //MemberServiceTest 에서 만든 MemoryMemberRepository() 가 다른 인스턴스
    //같은 인스턴스를 사용하도록 변경
    @Autowired MemberRepository memberRepository;

    @Test
    @Commit
    void 회원가입() {
        //given
        Member member = new Member();
        member.setName("spring101");//spring으로 해버리면 이미 join 되어있기 때문에 테스트 오류 -> afterEach, BeforeEach보다

        //when
        Long saveId = memberService.join(member);

        //then
        Member findMember = memberService.findOne(saveId).get();
        //Assertions은 static import처리 -> Alt + Enter
        assertThat(member.getName()).isEqualTo(findMember.getName());

    }

    @Test
    public void 중복_회원_예외() {
        //given
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");//중복확인을 위해 위와 동일하게 spring

        //when
        memberService.join(member1);
        //IllegatStateException이 발생되어야 함
        //assertThrows(IllegalStateException.class, () -> memberService.join(member2));
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));

        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");

/*        try {
            memberService.join(member2);//실행되고 exception으로 안간다면 fail 처리
            fail();

        } catch (IllegalStateException e) {
            //assertThat 만 안됨..? -> static import 할 것 -> Alt + Enter
            Assertions.assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
        }
*/

        //then
    }

    @Test
    void findMembers() {
    }

    @Test
    void findOne() {
    }
}