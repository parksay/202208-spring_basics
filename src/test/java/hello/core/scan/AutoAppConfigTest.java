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

        // * 중복 / 충돌 경우
        // 자동 bean 등록할 때 name을 지정해줄 수 있다고 했지. 그렇게 이름 따로 등록하다가 서로 이름이 겹치면...?
        // 자동 Bean vs 자동 Bean => 충돌 난다고 에러 터짐
        // 자동 Bean vs 수동 Bean => 자동 Bean 을 수동 Bean 이 override 해버리고 로그 메세지 하나 남김
        // 대신 이게 진짜로 개발자가 이렇게 의도를 해서 만들어 둔 거일 수도 있지만 실수로 이렇게 해둔 거일 수도 있잖아..?
        // 그럴 때를 대비해서 설정을 따로 해둘 수 있음.
        // Bean 등록이 중복되거나 충돌했을 때 (자동 Bean vs 수동 Bean 포함) 어떻게 대처할지를 설정해둘 수 있음.
        // Spring Boot 에서 설정해둔 기본값은 어떤 충돌이든 Bean 등록끼리 충돌하면 무조건 예외 터뜨리고 실행 멈춤. 이러한 동작이 default
        // spring 이 왜 default 값을 이렇게 설정해 뒀겠어
        // 개발은 혼자하는 게 아님. 나는 코드를 다 꿰뚫고 있고 다 알고 있어도 다른 개발자는 또 담당 기능이 다르다 보면 모르고 쓸 수도 있고 하다 보면 에러나기 쉬움.
    }
}
