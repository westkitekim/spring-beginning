package hello.hellospring;
//자바 코드로 직접 스프링 빈 등록하기

import hello.hellospring.aop.TimeTraceAop;
import hello.hellospring.repository.*;
import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;

//Application 설정 코드(어셈블리라도고 함)
@Configuration
public class SpringConfig {

//    방법1.
//    private DataSource dataSource;
//
//
//    @Autowired
//    public SpringConfig(DataSource dataSource) {
//        this.dataSource = dataSource;
//    }

//    방법2.
//    @PersistenceContext//원래는 이 어노테이션으로 DI 하지만 여기서는 Autowired으로 주입
//    private EntityManager em;
//
//    @Autowired
//    public SpringConfig(EntityManager em) {
//        this.em = em;
//    }

    //SpringData가 구현체를 만들어놓은게 등록됨
    private final MemberRepository memberRepository;

    public SpringConfig(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    //Spring Bean 에 등록된 MemberRepository를 MemberService에 주입한다
    @Bean//MemberService 객체 등록
    public MemberService memberService() {
        return new MemberService(memberRepository);
    }

//    @Bean//위의 MemberService에 들어가야함(생성자 인자로)
//    public MemberRepository memberRepository() {
//        return new MemoryMemberRepository();
//        return new JdbcMemberRepository(dataSource);//생성자로 dataSource 필요 - spring이 제공해줌
//        return new JdbcTemplateMemberRepository(dataSource);
//        return new JpaMemberRepository(em);

//    }

}
