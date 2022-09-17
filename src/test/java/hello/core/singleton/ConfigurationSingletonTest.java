package hello.core.singleton;

import hello.coreSpring.AppConfig;
import hello.coreSpring.member.MemberRepository;
import hello.coreSpring.member.MemberServiceImpl;
import hello.coreSpring.order.OrderServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ConfigurationSingletonTest {


    @Test
    void configurationTest (){
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

        MemberServiceImpl memberService =  ac.getBean("memberService", MemberServiceImpl.class);
        OrderServiceImpl orderService = ac.getBean("orderService", OrderServiceImpl.class);
        MemberRepository memberRepository1 = memberService.getMemberRepository();
        MemberRepository memberRepository2 = orderService.getMemberRepository();

        System.out.println("memberService > memberRepository1 = " + memberRepository1);
        System.out.println("orderService > memberRepository2 = " + memberRepository2);

        // 그럼 memberService 에서 들고 있는 memberRepository 랑, orderService 가 들고 있는 memberRepository 랑, 스프링 콘테이너가 등록해 놓고 관리하고 있던 memberRepository 랑, 셋이 같을까?
        MemberRepository memberRepository = ac.getBean("memberRepository", MemberRepository.class);
        System.out.println("memberRepository = " + memberRepository);
        // 같네.

        Assertions.assertThat(memberService.getMemberRepository()).isSameAs(memberRepository);
        Assertions.assertThat(orderService.getMemberRepository()).isSameAs(memberRepository);
        // 그럼 이게 혹시 설마 메소드를 다 안 부르나...?
        // 그럼 메소드 실행할 때 로그를 남기게 해 보자.
        // => AppConfig.java
        // 거기에  System.out.println("call className.methodName"); 해서 찍어 봤음
        // 그랬더니 memberRepository 는 세 번 호출되는 게 맞는데 한 번만 실행됨...;
        // 또잉...? 이건 또 뭐야. 어떻게 이런 게 가능하지?
        // @Configuration > 바이트코드 라는 걸 씀.
        // => 밑에 configurationDepp()
    }

    @Test
    void configurationDeep() {
        // 비밀은 바로 @Configuration 어노테이션이 붙어 있는 AppConfig.class 에 있음.
        // 로그를 한번 찍어 보자.
        ApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
        AppConfig bean = ac.getBean(AppConfig.class);

        // 우리가 예상하는 값은...?
        // "class hello.coreSpring.AppConfig" 가 나와야 할 것 같지
        // 근데 실제 나오는 값은..?
        System.out.println("bean = " + bean.getClass());
        // "class hello.coreSpring.AppConfig$$EnhancerBySpringCGLIB$$ad5a98cb"
        // 뭔가 훨씬 복잡해졌음...
        // 중간에 뭐 $$EnhancerBySpringCGLIB$$ 이런 것도 붙고
        // 왜냐하면 스프링이 @Configuration 을 등록하면서 자체적으로 바이트 코드 조작 라이브러리를 써서 등록하기 때문
        // 사실은 우리가 만든 AppConfig 클래스 자체를 등록하는 게 아니고, 그 클래스를 상속받은 다른 클래스를 만들어서 그 복사한 클래스를 등록함.
        // 참고로 AppConfig 로 조회해서 bean 이 튀어나올 수 있었던 이유는 AppConfig 를 상속받아서 만들었기 때문. 부모 타입으로 조회하면 자식 타입 다 딸려 나온다고 했으니까.
        // 그렇게 복사하면서 뭔가 다른 코드가 추가되었겠지.
        // 추측해 보자면, 처음 호출할 때는 내가 진짜로 만든 생성자를 실행해서 만들어서 반환해주고, 이미 객체가 있다면 전에 만들어 둔 객체를 반환하는, 그런 로직을 추가해 뒀겠지.

        // 이러한 비밀이 @Configuration 에 있다고 했지.
        // 그럼 @Configuration 를 주석처리하고 @Bean 만 달아 두면 어떻게 됨...?
        // 해보니까 어때.
        // 이제는 진짜로 우리가 직접 짰던 코드가 실행이 되지.
        // new 생성자로 하나하나씩 만들고.
        // AppConfig 로 등록된 bean 도 꺼내보니까 아까와는 다르게 실제로 내가 만든 그 클래스가 등록돼 있음.
        // class hello.coreSpring.AppConfig
        // @Bean 만 있어도 등록 자체는 되긴 하는데, 문제가 memberRepository 가 세 번씩 호출되고 막 이래.
        // 결과적으로 위에서 실행해 봤던 configurationTest 테스트도 다시 실행해 보니까 memberService / orderService / memberRepository 셋이 서로 다 달라
        // 더 이상 스프링에서 싱글톤을 보장해주지 않아.
    }
}
