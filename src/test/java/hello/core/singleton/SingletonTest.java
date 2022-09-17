package hello.core.singleton;

import hello.coreSpring.AppConfig;
import hello.coreSpring.member.MemberService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.Assertions.*;

public class SingletonTest {

    // 애플리케이션에는 여러 가지 종류가 있음.
    // 배치 프로그램, 앱 애플리케이션, 웹 애플리케이션, 유틸 애플리케이션
    // 그중에서 웹 애플리케이션은 요청과 응답이 끊임없이 일어나는 애플리케이션임.
    // 웹 애플리케이션에서 왜 싱글톤을 사용해야 하는지, 싱글톤이 뭔지, 어떻게 만들 수 있는지 배울 것.
    @Test
    @DisplayName("스프링 없는 순수한 DI 컨테이너")
    void pureContainer() {
        AppConfig appConfig = new AppConfig();
        // 1. 조회1 - 호출할 때마다 객체를 생성함
        MemberService memberService1 = appConfig.memberService();

        // 2. 조회 - 호출할 때마다 객체를 생성함
        MemberService memberService2 = appConfig.memberService();

        // 두 객체가 가지는 주소가 다름을 확인
        System.out.println("memberService1 = " + memberService1);
        System.out.println("memberService2 = " + memberService2);

        // 지금은 이게 두 개지만 접속자가 100명, 1000명, 10만 이렇게 되면 클라이언트가 요청할 때마다 객체를 하나씩 생성해야 하는 건데 서버에 부담이 안 될 수가 없겠지
        // 요즘은 컴퓨터 성능이 좋아져서 크게 티는 안 난다고는 해도...

        // 검증해  - 위에서 print 로 확인했어도, 그건 눈으로 한 거고, 항상 자동화 해야 함.
        assertThat(memberService1).isNotSameAs(memberService2);

    }

    @Test
    @DisplayName("싱글톤 패턴대로 설계한 클래스와 그 객체")
    void SingletonServiceTest() {
        // SingletonService 의 인스턴스를 여러 번 따로 불러서 받아오기
        SingletonService singletonService1 =  SingletonService.getInstance();
        SingletonService singletonService2 =  SingletonService.getInstance();

        // 두 번 부른 인스턴스 같은지 주소값 비교해 보기 - 일단 출력해서 눈으로만
        System.out.println("singletonService1 = " + singletonService1);
        System.out.println("singletonService2 = " + singletonService2);

        // 검증 코드로 자동화하기
        assertThat(singletonService1).isSameAs(singletonService2);
        // isEqualTo 도 있고 isSameAs 있는데 뭐가 다른가요?
        // equal 은 자바에 있는 함수 equal 로 비교하는 결과인 거고, same 은 비교 구문 ( == ) 으로 비교하는 결과인 거고

        // 이렇게 테스트 코드 완성하고 나면 메소드 단위에서 한번 실행해 보고 성공하면 클래스 레벨에서 실행해서 한 번 더 보고
        // 왜냐하면 코드 짜면서 이것저것 만지고 고치다 보면 그 클래스에 있는 다른 코드들에서 오류가 날 확률이 가장 크니까

    }

    @Test
    @DisplayName("스프링 컨테이너와 싱글톤")
    void springContainer() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

        // 1. 조회1 - 스프링이 만들어 둔 객체를 받아옴
        MemberService memberService1 = ac.getBean("memberService", MemberService.class);
        // 2. 조회2 - 스프링이 만들어 둔 객체를 받아옴
        MemberService memberService2 = ac.getBean("memberService", MemberService.class);

        System.out.println("memberService1 = " + memberService1);
        System.out.println("memberService2 = " + memberService2);

        // 검증
        assertThat(memberService1).isSameAs(memberService2);
        
        // 우리가 SingletonService.class 에서 썼던 복잡한 코드들, private 넣고 static 넣고...
        // 그런 건 안 해도 spring은 자동으로 빈을 싱글톤으로 관리해줌
    }

}
