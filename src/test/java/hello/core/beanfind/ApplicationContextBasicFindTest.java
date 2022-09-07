package hello.core.beanfind;

import hello.coreSpring.AppConfig;
import hello.coreSpring.member.MemberService;
import hello.coreSpring.member.MemberServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ApplicationContextBasicFindTest {

    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);


    @Test
    @DisplayName("빈 이름으로 조회")
    void findBeanByName() {
        // applicationContext 에서 "memberService" 라는 이름으로 bean을 받아옴. 
        // 그 빈은 memberService 라는 변수에 담음
        MemberService memberService = ac.getBean("memberService", MemberService.class);
        System.out.println("memberService = " + memberService);
        System.out.println("MemberService.getClass() = " + memberService.getClass());
        // 우리가 ac에서 받아온 memberService 안에 담긴 물체가 MemberServiceImpl 의 구현체인지
        assertThat(memberService).isInstanceOf(MemberServiceImpl.class);
    }

    @Test
    @DisplayName("이름 없이 타입으로만 조회")
    void findBeanByType() {
        // applicationContext 에서 MemberService 라는 타입으로 bean을 받아옴. 인터페이스를 넣으면 그 인터페이스의 구현체를 받아옴.
        // 그 빈은 memberService 라는 변수에 담음
        MemberService memberService = ac.getBean(MemberService.class);
        System.out.println("memberService = " + memberService);
        System.out.println("MemberService.getClass() = " + memberService.getClass());
        // 우리가 ac에서 받아온 memberService 안에 담긴 물체가 MemberServiceImpl 의 구현체인지
        assertThat(memberService).isInstanceOf(MemberServiceImpl.class);
    }


    @Test
    @DisplayName("구체 타입으로 조회")
    void findBeanByName2() {
        // applicationContext 에서 MemberService 라는 타입으로 bean을 받아옴.
        // 자료형을 이렇게 지정하면 그 자료형에 해당하는 빈을 받아옴
        MemberServiceImpl memberService = ac.getBean("memberService", MemberServiceImpl.class);
        assertThat(memberService).isInstanceOf(MemberServiceImpl.class);

        // @Bean
        // public MemberService memberService() {
        //    return new MemberServiceImpl(memberRepository());
        // }
        // 우리가 AppConfig 에서 위처럼 등록했다고 해서,
        // 그러니까 method 에서 return type을 MemberService라고 지정했다고 해서
        // 그 bean을 받을 때도 꼭 MemberService로 받을 필요는 없음.
        // 그 return 값 객체를 들고 있는 거고 그 객체의 타입으로 찾는 거라서
    }


    @Test
    @DisplayName("빈 이름으로 조회 실패 테스트")
    void findBeanByNameX() {
        // applicationContext 에서 말도 안 되는 이름으로 bean을 받아옴.
        // MemberService xxx = ac.getBean("XXX", MemberService.class);
        // System.out.println("memberService = " + xxx);
        // 이러면 어때? 예외 뜨지.
        // '이러한 잘못된 코드를 실행하면 이러한 예외가 떠야 한다' 는 걸 테스트 케이스로 만들어 보기
        assertThrows(NoSuchBeanDefinitionException.class,
                ()->{ ac.getBean("XXX", MemberService.class); });

    }
}
