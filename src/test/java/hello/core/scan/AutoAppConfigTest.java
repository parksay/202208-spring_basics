package hello.core.scan;

import hello.coreAutoConfig.AutoAppConfig;
import hello.coreAutoConfig.member.MemberService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class AutoAppConfigTest {

    @Test
    void basicScan() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AutoAppConfig.class);
        // 다 똑같이 쓰는데 AnnotationConfigApplicationContext 만들 때 파라미터로 AutoAppConfig 넣어준다는 것만 다름
        MemberService memberService = ac.getBean(MemberService.class);
        Assertions.assertThat(memberService).isInstanceOf(MemberService.class);

        // 로그 보면 아래처럼 나옴.
        // 15:56:50.507 [main] DEBUG org.springframework.beans.factory.support.DefaultListableBeanFactory - Creating shared instance of singleton bean 'memoryMemberRepository'
        //15:56:50.508 [main] DEBUG org.springframework.beans.factory.support.DefaultListableBeanFactory - Autowiring by type from bean name 'memberServiceImpl' via constructor to bean named 'memoryMemberRepository'
        //15:56:50.509 [main] DEBUG org.springframework.beans.factory.support.DefaultListableBeanFactory - Creating shared instance of singleton bean 'orderServiceImpl'
        //15:56:50.509 [main] DEBUG org.springframework.beans.factory.support.DefaultListableBeanFactory - Autowiring by type from bean name 'orderServiceImpl' via constructor to bean named 'memoryMemberRepository'
        //15:56:50.509 [main] DEBUG org.springframework.beans.factory.support.DefaultListableBeanFactory - Autowiring by type from bean name 'orderServiceImpl' via constructor to bean named 'rateDiscountPolicy'
        // Creating shared instance of singleton bean '~~~~'
        // '~~~~' 클래스를 싱글톤 Bean 으로 생성 중입니다
        // Autowiring by type from bean name '~~~~' via constructor to bean named '~~~'
        // Autowired 보고 의존관계 주입합니다
        // Bean 이름은 클래스 이름에서 앞 글자만 소문자로 바뀐 이름
        // 물론 어노테이션에 속성값 넣어서 따로 지정해서 등록해줄 수 있음
    }
}
