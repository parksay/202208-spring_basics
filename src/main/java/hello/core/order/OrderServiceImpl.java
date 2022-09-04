package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;

public class OrderServiceImpl implements OrderService {
    // 주문을 생성하는 역할의 구현체

    // private final MemberRepository memberRepository = new MemoryMemberRepository();
    // private final DiscountPolicy discountPolicy = new FixDiscountPolicy();
    // 원래는 할인해주는 금액이 고정되어 있는 고정 할인 정책 FixDiscountPolicy 를 사용했음
    // 나중에 요구 사항이 바껴서 구매 금액에 따라서 일정한 비율만큼 할인해 주는 비율 할인 정책을 사용하기로 함.
    // private final DiscountPolicy discountPolicy = new RateDiscountPolicy();
    // FixDiscountPolicy()에서 RateDiscountPolicy()로 변경하려니까 OrderServiceImpl 안에서 코드를 수정해야 함.
    // DiscountPolicy 라는 인터페이스도 등장하고, FixDiscountPolicy나 RateDiscountPolicy 같은 구현 클래스도 등잠함.
    // DiscountPolicy 라는 인터페이스 안에 FixDiscountPolicy를 넣어주고 있던 걸 RateDiscountPolicy가 들어가도록 고쳐야 함.
    // 이렇게 해서 보니까 OCP와 DIP를 위반하고 있음.
    // OCP : 소프트웨어 요소는 확장에는 열려 있으나 변경에는 닫혀 있어야 함. / DIP : 쉽게 이야기해서, 구현체에 의존하지 말고 인터페이스에 의존하라는 말.
    // 우리가 원하는 건 정책이 FixDiscountPolicy에서 RateDiscountPolicy로 바뀌었다고 하더라도 클라이언트 쪽의 코드 OrderServiceImpl 안에서는 코드가 바뀌는 내용이 없어야 함.
    // 그렇게 되려면 무엇을 어떻게 수정해야 하는가?
    // DIP는 쉬움. 그냥 구현 클래스는 지우고 인터페이스만 남기면 됨.
    // private DiscountPolicy discountPolicy;
    // 이렇게 하면 구현 클래스인 FixDiscountPolicy나 RateDiscountPolicy는 등장하지 않고, 인터페이스인 DiscountPolicy만 등장함.
    // 보니까 DIP는 달성함. 그런데 이 상태로 코드 실행하면 당연히 에러나겠지. DiscountPolicy 안에 들어 있는 구현체가 없으니까 NullPointerException 뜨겠지. null에다가 . 찍어서 뭐 실행하면 NPE 뜨는 거 아냐.
    // 그럼 이걸 어떻게 해결해야 할까...?


    // 프로그램을 하나의 공연이라고 생각해 보자. 서비스를 하나 만들어서 올리는 것과 공연을 하나 기획해서 올리는 것은 비슷한 점이 많다.
    // 레오나르도 디카프리오가 로미오 역할이다. 디카프리오는 줄리엣 역할을 누가 맡아서 할지 상대 배우까지 직접 알아보고 섭외하고 결정해서 초빙한다.
    // 이거는 말이 안 된다. 월권이라고 하면 월권이고, 과다 책임이라고 하면 과다 책임이다. 디카프리오는 배우이고 로미오 역할만 잘 수행하면 된다. 줄리엣 배우를 누가 맡을지는 공연 기획자가 결정해야 한다.
    // 지금 OrderServiveImpl 이 하고 있는 것도 똑같다.
    // OrderServiceImpl 은 누가 무엇을 얼마에 샀는지 정보를 바탕으로 주문 정보를 생성해주는 역할을 한다.
    // 여기서 할인 정책 DiscountPolicy 에 고정 할인 정책 FixDiscountPolicy을 적용할지, 비율 할인 정책 RateDiscountPolicy을 적용할지는 서비스 매니저가 할 일이지, OrderServiceImpl가 결정할 문제가 아니다.
    // 그런데 코드 내용을 보면 OrderServiceImpl는 DiscountPolicy 안에 RateDiscountPolicy를 직접 결정해서 넣어주고 있다.
    // 이것은 월권이기도 하고, 원래 맡았던 역할보다 더 많은 일을 시키는 과다 책임이기도 하다.
    // 이러한 문제를 해결하기 위해 AppConfig 라는 공연 기획자를 따로 만들어 두기로 한다.

    private final MemberRepository memberRepository;
    private final DiscountPolicy discountPolicy;
    public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
        // 변수에 final 이 붙어 있으면 선언할 때 넣어주거나 생성자에서 넣어주거나 무조건 둘 중에 하나.
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
        // 이렇게 생성자 호출할 때 넣어주는 방식을 '생성자 주입'이라고 함.
    }

    // 이렇게 하면 OrderServiceImpl 입장에서는 memberRepository 에 뭐가 들어올지, discountPolicy 에 뭐가 들어올지, 아무 것도 모르는 상태.
    // 외부에서 무슨 일이 벌어지는지는 모르는 채로 그냥 자기가 맡은 역할만 충실하게 수행할 뿐.
    // 연극으로 치면 레오나르도 디카프리오는 줄리엣 역할을 엠마 스톤이 맡을지, 올리비가 맡을지 아무 것도 모르는 채로 그냥 로미오 역할에만 충실하면 됨.


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
}
