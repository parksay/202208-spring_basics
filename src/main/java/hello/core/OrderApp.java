package hello.core;

import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.order.Order;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;

public class OrderApp {

    public static void main(String[] args) {
        // MemberService memberService = new MemberServiceImpl();
        // OrderService orderService = new OrderServiceImpl();
        // 원래 코드는 위쪽. App 이 직접 MemberServiceImpl 와 OrderServiceImpl 를 생성함.
        // 그러면 MemberServiceImpl 는 MemoryMemberRepository 를 생성하고, OrderServiceImpl는 MemoryMemberRepository와 FixDiscountPolicy 를 생성했음.
        // 마치 연쇄 반응처럼. 사슬 구조였지.
        // 그런데 지금은 AppConfig 에서 다 받아다 쓰도록 바꾸기.
        AppConfig appConfig = new AppConfig();
        MemberService memberService = appConfig.memberService();
        OrderService orderService = appConfig.orderService();

        Long memberId = 1L;
        // 회원 정보 하나를 생성
        Member member =  new Member(memberId, "MemberA", Grade.VIP);
        // 그 회원 정보를 데이터베이스에 추가
        memberService.join(member);

        // 회원, 상품, 가격 -> 누가 무엇을 얼마에 샀는지 정보를 가지고 주문 정보 생성
        Order order = orderService.createOrder(memberId, "itemA", 20000);

        // 주문한 결과 가격을 알아서 잘 계산해 줬는지 확인
        System.out.println("order = " + order);
        System.out.println("order = " + order.calculatePrice());
    }


}
