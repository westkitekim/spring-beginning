package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;
//이전에 실행한 내용 재실행 : Ctrl + R (Mac), Shift + F10(Window)
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
//Ctrl + Shift + T 로 테스트 클래스 자동 생성, 똑같은 패키지도 생성한다
//Assertions는 assertj 패키지를 사용함, junit 아님
class MemberServiceTest {
    //테스트는 영어권에서 일하는 것이 아니면 한글로 보통 많이 작성한다. 직관적,
    //빌드될 때 테스트 코드는 실제 코드에 포함되지 않는다

    //Test : 1. given, 2. when, 3. then 을 기억한다 !!
    //given : 이러한 상황이 주어짐, when : 이것을 실행했을 때, then : 결과가 ~ 것이 나와야 한다
    //Test는 정상수행 확인도 중요하지만 예외가 잘 수행되는 지도 중요!
    MemberService memberService;//MemberService만 있으면 clear 안됨
    //new 로 자꾸 생성하면 내용물이 달라질 수 도 있는 등 위험성이 존재
    //즉, MemberServivce에서 만든 new MemoryMemberRepository()와
    //MemberServiceTest 에서 만든 MemoryMemberRepository() 가 다른 인스턴스
    //같은 인스턴스를 사용하도록 변경
    MemoryMemberRepository memberRepository;
    //동작하기 전(메서드 실행 전)에 넣어주기, 실행전에 수행됨
    @BeforeEach
    public void beforeEach() {
        memberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memberRepository);
    }

    //Test가 끝날 때마다 리포지토리를 깔끔하게 지워주는 코드를 필요
    @AfterEach //메서드 실행 끝날때마다 동작되는 코드
    public void afterEach() {
        memberRepository.clearStore();
    }

    @Test
    void 회원가입() {
        //given
        Member member = new Member();
        member.setName("spring");//spring으로 해버리면 이미 join 되어있기 때문에 테스트 오류

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