package hello.coreAutoConfig.discount;

import hello.coreAutoConfig.member.Grade;
import hello.coreAutoConfig.member.Member;

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

}
