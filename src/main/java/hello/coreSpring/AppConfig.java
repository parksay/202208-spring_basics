package hello.coreSpring;

import hello.coreSpring.discount.DiscountPolicy;
import hello.coreSpring.discount.RateDiscountPolicy;
import hello.coreSpring.member.MemberRepository;
import hello.coreSpring.member.MemberService;
import hello.coreSpring.member.MemberServiceImpl;
import hello.coreSpring.member.MemoryMemberRepository;
import hello.coreSpring.order.OrderService;
import hello.coreSpring.order.OrderServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public MemberService memberService() {
        return new MemberServiceImpl(memberRepository());
    }

    @Bean
    public OrderService orderService() {
        return new OrderServiceImpl(memberRepository(), discountPolicy());
    }

    @Bean
    public MemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }

    @Bean
    public DiscountPolicy discountPolicy() {
        return new RateDiscountPolicy();
    }
}
