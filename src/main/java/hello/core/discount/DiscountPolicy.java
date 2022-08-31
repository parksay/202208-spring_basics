package hello.core.discount;

import hello.core.member.Member;

public interface DiscountPolicy {
    // 고정 금액만큼 할인해 줄지, 비율에 따라서 할인해 줄지 아직 정해지지 않음.

    /**
     *  멤버와 가격을 넘겨 주면 그 가격에서 얼마를 할인해 줄지 결정해서 되돌려줌.
     * @return 할인 대상 금액액     */
    int discount(Member member, int price);
}
