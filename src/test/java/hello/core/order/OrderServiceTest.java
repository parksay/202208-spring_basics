package hello.core.order;

import hello.core.AppConfig;
import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class OrderServiceTest {

    // MemberService memberService = new MemberServiceImpl();
    // OrderService orderService = new OrderServiceImpl();
    // 원래 코드는 위쪽. App 이 직접 MemberServiceImpl 와 OrderServiceImpl 를 생성함.
    // 그러면 MemberServiceImpl 는 MemoryMemberRepository 를 생성하고, OrderServiceImpl는 MemoryMemberRepository와 FixDiscountPolicy 를 생성했음.
    // 마치 연쇄 반응처럼. 사슬 구조였지.
    // 그런데 지금은 AppConfig 에서 다 받아다 쓰도록 바꾸기.

    MemberService memberService;
    OrderService orderService;

    @BeforeEach
    public void beforeEach() {
        AppConfig appConfig = new AppConfig();
        memberService = appConfig.memberService();
        orderService = appConfig.orderService();
    }

    @Test
    void createOrder() {
        long memberId = 1L;
        // 회원 정보 하나를 생성
        Member member = new Member(memberId, "memberA", Grade.VIP);
        // 그 회원 정보를 데이터베이스에 추가
        memberService.join(member);

        // 회원, 상품, 가격 -> 누가 무엇을 얼마에 샀는지 정보를 가지고 주문 정보 생성
        Order order = orderService.createOrder(memberId, "itemA", 10000);
        // 주문한 결과 가격을 알아서 잘 계산해 줬는지 확인
        Assertions.assertThat(order.getDiscountPrice()).isEqualTo(1000);
    }
}
