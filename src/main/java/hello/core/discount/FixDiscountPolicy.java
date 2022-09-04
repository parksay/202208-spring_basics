package hello.core.discount;

import hello.core.member.Grade;
import hello.core.member.Member;

public class FixDiscountPolicy implements DiscountPolicy {
    // 할인 정책 > 고정 금액만큼 할인해 주는 정책
    private int discountFixAmout = 1000; // 고정 할인 금액 1000원

    @Override
    public int discount(Member member, int price) { // 멤버와 가격을 넘겨 주면 그 가격에서 얼마를 할인해 줄지 결정해서 되돌려줌.
        if(member.getGrade() == Grade.VIP) {    // 회원 등급이 VIP면
            return discountFixAmout;    // 미리 정해 놓은 고정 할인 금액만큼 할인
        } else {    // VIP가 아니면
            return 0;   // 할인 금액 없음
        }
    }
    
    
    // intelliJ 단축키 - Alt + 1 / 2 / 3 / 4 / 5 ... Project Explorer 창이나 Console 창, 디버그창, 탐색창 등 열고 닫기 
}
