package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
//Test 아주 쉽게 하는 방법 : 클래스 이름에 커서 놓고 Ctrl + Shift + T
//데이터를 손 볼 때는 Transactional이 되어있어야 함
@Transactional
public class MemberService {
    //같은 인스턴스를 사용하기 위해 코드 변경
    //private final MemberRepository memberRepository = new MemoryMemberRepository();

    private MemberRepository memberRepository;

    //setter DI, 단점은 MemberController에서 확인
//    @Autowired
//    public void setMemberRepository(MemberRepository memberRepository) {
//        this.memberRepository = memberRepository;
//    }

    //외부에서 넣어주도록 변경, MemberRepository를 외부에서 주입 -> DI
    //구현체인 MemoryMemberRepository가 주입
    @Autowired//SpringConfig에서 MemberService가 @Bean으로 등록되어 있지 않다면 해당 어노테이션도 적용되지 않는다
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

//    public static void main(String[] args) {
//        //객체를 직접 생성하여 Spring Container 관리 하에 있지 않기 때문에 @Autowired 작동X
//        MemberService memberService = new MemberService();
//    }

    /**
     * JPA는 join 들어올 때 모든 데이터 변경이 transaction 안에서 이루어져야 함
     * 회원 가입
     */
    public Long join(Member member) {
        
        //방법2 로 정리 가능
        //메서드로 분리 : Shift + Ctrl + Alt + T
        validateDupicateMember(member);//중복 회원 검증

        /*
        //같은 이름이 있는 중복 회원X
        //자동 변수 추출 : Ctrl + Alt + V
        //Optional을 바로 꺼내는 방법은 좋지 않음
        Optional<Member> result = memberRepository.findByName(member.getName());

        //값이 있으면
        //Optional이기 때문에 다양한 method 사용 가능, Optioanl안에 member객체가 있는 것
        //과거엔 if(~~) == null이면으로 작성
        //result.orElseGet()도 많이 사용됨
        result.ifPresent(member1 -> {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        });
        */

        memberRepository.save(member);
        return member.getId();

    }

    private void validateDupicateMember(Member member) {
        memberRepository.findByName(member.getName())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }

    /**
     * 전체 회원 조회
     */
    public List<Member> findMembers() {

        return memberRepository.findAll();
    }

    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }
}
