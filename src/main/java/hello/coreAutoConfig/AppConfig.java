package hello.coreAutoConfig;

import hello.coreAutoConfig.discount.DiscountPolicy;
import hello.coreAutoConfig.discount.RateDiscountPolicy;
import hello.coreAutoConfig.member.MemberRepository;
import hello.coreAutoConfig.member.MemberService;
import hello.coreAutoConfig.member.MemberServiceImpl;
import hello.coreAutoConfig.member.MemoryMemberRepository;
import hello.coreAutoConfig.order.OrderService;
import hello.coreAutoConfig.order.OrderServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


//@Configuration 주석 처리한 상태로 실행하면  System.out.println("call className.methodName"); 로그 어떻게 찍히나...?
@Configuration
public class AppConfig {

    @Bean
    public MemberService memberService() {
        System.out.println("callAppConfig.memberService");
        return new MemberServiceImpl(memberRepository());
    }

    @Bean
    public OrderService orderService() {
        System.out.println("call AppConfig.orderService");
        return new OrderServiceImpl(memberRepository(), discountPolicy());
    }

    @Bean
    public MemberRepository memberRepository() {
        System.out.println("call AppConfig.memberRepository");
        return new MemoryMemberRepository();
    }

    @Bean
    public DiscountPolicy discountPolicy() {
        System.out.println("call AppConfig.discountPolicy");
        return new RateDiscountPolicy();
    }

    // 이거 보면 차근차근 한번 봐 보자.
    // 일단 memberService() 를 실행하겠지.
    // 그럼 그 안에 있는 memberRepository()도 실행하게 되겠지.
    // 스프링이라고 해서 뭐 새로운 법을 만들어 버리는 게 아니고, 어쨌든 Java 코드기 때문에 그 안에서 움직이겠지
    // 그러고 나서 orderService() 도 실행하겠지.
    // 근데 이제 보니까 그 안에 또 memberRepository()가 있어.
    // 그럼 벌써 memberRepository()가 두 번이나 실행되고 객체도 두 개가 생성되고 싱글톤 디자인이니 뭐니 하는 것도 사라지는 거 아니냐..?
    // 글쎄 모르겠네. 그럴 때는 그냥 일단 직접 test 코드 짜서 실험해 보기.
    // 이거 어떻게 확인해 볼까..?
    // memberServiceImpl 이 들고 있는 memberRepository 랑 orderServiceImpl 이 들고 있는 memberRepository 랑 슬쩍 꺼내서 한번 비교해 보기.
    // => memberServiceImpl.class > getMemberRepository() /  orderServiceImpl.class  > getMemberRepository()
    // => ConfigurationSingletonTest.class
}
