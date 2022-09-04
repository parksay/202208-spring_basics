package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;

public class OrderServiceImpl implements OrderService {
    // 주문을 생성하는 역할의 구현체

    private final MemberRepository memberRepository = new MemoryMemberRepository();
    private final DiscountPolicy discountPolicy = new FixDiscountPolicy();
    // 주문을 하면 주문한 사람 정보를 불러 옴.
    // 주문자 정보를 바탕으로 VIP인지 일반인지 판단해서 얼마나 할인해 줄지 결정.
    // 주문자 정보, 주문 상품 정보, 원래 가격, 할인된 가격을 바탕으로 주문 정보를 되돌려줌.

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        // 누가 무엇을 사려고 하는데 얼마를 할인해 주면 되나요?
        int discountPrice = discountPolicy.discount(member, itemPrice);

        return new Order(memberId, itemName, itemPrice, discountPrice);
    }
}
