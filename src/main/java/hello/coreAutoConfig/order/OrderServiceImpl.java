package hello.coreAutoConfig.order;

import hello.coreAutoConfig.discount.DiscountPolicy;
import hello.coreAutoConfig.member.Member;
import hello.coreAutoConfig.member.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderServiceImpl implements OrderService {
    //  주문 정보를 관리하는 역할의 구현체




    private final MemberRepository memberRepository;
    private final DiscountPolicy discountPolicy;

    @Autowired
    // 생성자에 이렇게 해 두면 자동으로 파라미터들에 의존관계 주입해 줌
    // 생성자 파리미터에 들어 있는 애들을 type 으로 검색해서 맞는 애들 넣어줌
    public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }



    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        // 주문을 하면 주문한 사람 정보를 불러 옴.
        // 주문자 정보를 바탕으로 VIP인지 일반인지 판단해서 얼마나 할인해 줄지 결정.
        // 주문자 정보, 주문 상품 정보, 원래 가격, 할인된 가격을 바탕으로 주문 정보를 되돌려줌.
        Member member = memberRepository.findById(memberId);
        // 누가 무엇을 사려고 하는데 얼마를 할인해 주면 되나요?
        int discountPrice = discountPolicy.discount(member, itemPrice);

        return new Order(memberId, itemName, itemPrice, discountPrice);
    }

    public MemberRepository getMemberRepository() {
        return this.memberRepository;
    }
    
}
