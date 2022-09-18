package hello.coreAutoConfig;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan (
    excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Configuration.class)
)
// 위 코드는 컴포넌트로 등록하는 대상에서 제외할 조건 넣는 코드
// AppConfig 우리가 수동으로 Bean 등록하는 연습 했던 class 인데 이 class 에 붙은 @Configuration 어노테이션 안에 까 보면 @Component 어노테이션이 붙어 있음.
// 그래서 일단 예외 처리 해 두는 거.
// 실무에서는 사실 이렇게 예외 대상을 잘 두지는 않음.
// 지금 테스트해볼 때 이게 진짜로 우리가 테스트 하고 싶은 class 에서 등록된 건지, 이전에 짜 두었던 수동 등록 class 에서 등록된 건지 모르니까 이렇게 해둔 거.
public class AutoAppConfig {
    // 우리 AppConfig 에서 Bean 등록하는 법을 배웠지.
    // class 하나 하나 만들고 Bean 등록할 때는 또 이러이러한 코드 추가해 주고...
    // 근데 서비스 규모가 클수록 만들어야 하는 클래스도 많고, 그 클래스들 또 하나하나 다 Bean 등록해 줘야 하고..
    // 누락될 수도 있고 번거롭기도 하고 귀찮고... 실수가 발생하기 쉬움
    // 그래서 스프링에서는 또 그 문제를 해결할 수 있는 기능을 제공함
    // 알아서 쫙 불러와서 Bean 등록 싹 해주고
    // 그러한 기능을 제공하는 어노테이션이 @ComponentScan
    // @ComponentScan 어노테이션이 붙어 있으면 @Component 어노테이션이 붙은 클래스들 싹 불러서 등록함.
    // 테스트 하려면 @Component 어노테이션을 붙여야겠지.
    // 나중에 @Component 어노테이션을 붙인 클래스들 아래 리스트
    // MemoryMemberRepository.class / RateDiscountPolicy.class / MemberServiceImpl.class



}
