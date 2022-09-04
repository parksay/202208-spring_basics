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
        MemberService memberService = new MemberServiceImpl();
        OrderService orderService = new OrderServiceImpl();

        Long memberId = 1L;
        // 회원 정보 하나를 생성
        Member member =  new Member(memberId, "MemberA", Grade.VIP);
        // 그 회원 정보를 데이터베이스에 추가
        memberService.join(member);

        // 회원, 상품, 가격 -> 누가 무엇을 얼마에 샀는지 정보를 가지고 주문 정보 생성
        Order order = orderService.createOrder(memberId, "itemA", 10000);

        // 주문한 결과 가격을 알아서 잘 계산해 줬는지 확인
        System.out.println("order = " + order);
        System.out.println("order = " + order.calculatePrice());
    }


}
