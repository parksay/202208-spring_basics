package hello.core;

import hello.core.discount.FixDiscountPolicy;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.member.MemoryMemberRepository;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;

public class AppConfig {

    // OrderServiceImpl를 보자.
    // 프로그램을 하나의 공연이라고 생각해 보자. 서비스를 하나 만들어서 올리는 것과 공연을 하나 기획해서 올리는 것은 비슷한 점이 많다.
    // 레오나르도 디카프리오가 로미오 역할이다. 디카프리오는 줄리엣 역할을 누가 맡아서 할지 상대 배우까지 직접 알아보고 섭외하고 결정해서 초빙한다.
    // 이거는 말이 안 된다. 월권이라고 하면 월권이고, 과다 책임이라고 하면 과다 책임이다. 디카프리오는 배우이고 로미오 역할만 잘 수행하면 된다. 줄리엣 배우를 누가 맡을지는 공연 기획자가 결정해야 한다.
    // 지금 OrderServiveImpl 이 하고 있는 것도 똑같다.
    // OrderServiceImpl 은 누가 무엇을 얼마에 샀는지 정보를 바탕으로 주문 정보를 생성해주는 역할을 한다.
    // 여기서 할인 정책 DiscountPolicy 에 고정 할인 정책 FixDiscountPolicy을 적용할지, 비율 할인 정책 RateDiscountPolicy을 적용할지는 서비스 매니저가 할 일이지, OrderServiceImpl가 결정할 문제가 아니다.
    // 그런데 코드 내용을 보면 OrderServiceImpl는 DiscountPolicy 안에 RateDiscountPolicy를 직접 결정해서 넣어주고 있다.
    // 이것은 월권이기도 하고, 원래 맡았던 역할보다 더 많은 일을 시키는 과다 책임이기도 하다.
    // 이러한 문제를 해결하기 위해 AppConfig 라는 공연 기획자를 따로 만들어 두기로 한다.


    // MemberServiceImpl도 마찬가지.
    // 프로그램을 하나의 공연이라고 생각해 보자. 서비스를 하나 만들어서 올리는 것과 공연을 하나 기획해서 올리는 것은 비슷한 점이 많다.
    // 레오나르도 디카프리오가 로미오 역할이다. 디카프리오는 줄리엣 역할을 누가 맡아서 할지 상대 배우까지 직접 알아보고 결정해서 섭외하고 초빙한다.
    // 이거는 말이 안 된다. 월권이라고 하면 월권이고, 과다 책임이라고 하면 과다 책임이다. 디카프리오는 배우이고 로미오 역할만 잘 수행하면 된다. 줄리엣 배우를 누가 맡을지는 공연 기획자가 결정해야 한다.
    // 지금 MemberServiceImpl 이 하고 있는 것도 똑같다.
    // MemberServiceImpl 은 회원 정보를 생성하하거나 저장하거나 조회하는 역할을 한다.
    // 여기서 어떤 매체에 저장할지, 메모리에 잠깐 저장할지 DataBase 에 저장할지, 외부 DB를 쓸지, 그건 MemberServiceImpl가 정할 일이 아니다. 서비스 매니저가 할 일이다.
    // 그런데 코드 내용을 보면 MemberServiceImpl는 MemberRepository 안에 MemoryMemberRepository를 쓸 거라고 자기가 직접 결정해서 넣어주고 있다.
    // 이것은 마치 로미오 역할 배우인 레오나르도 디카프리오가 상대 역할 줄리엣을 누가 맡아서 할지 여배우를 직접 알아보고 결정해서 섭외하고 초빙까지 하는 것과 같다.
    // 이러한 구조는 월권이기도 하고, 원래 맡았던 역할보다 더 많은 일을 시키는 과다 책임이기도 하다.
    // 이러한 문제를 해결하기 위해 AppConfig 라는 공연 기획자를 따로 만들어 두기로 한다.

    public MemberService memberService() {


        return new MemberServiceImpl(new MemoryMemberRepository());
        // 이렇게 함으로써 DIP가 완성.
        // MemberService 라는 인터페이스가 있고, 그 구현체는 MemberServiceImpl 이 있었어.
        // MemberServiceImpl 안에는 MemberRepository 가 있고, 그 안에 MemoryMemberRepository 를 넣는 일까지 다 직접 했었어.
        // MemberServiceImpl 는 여전히 MemberRepository 에 의존하지만 그 안에 무엇을 넣어줄지는 AppConfig 가 결정해서 생성자 호출할 때 넣어줘.
        // MemberServiceImpl 는 AppConfig 가 넣어주는 구현체를 받아다가 쓰기만 하는 구조야.
        // AppConfig 는 MemoryMemberRepository 객체를 생성해. 그리고 그 참조값을 MemberServiceImpl 에 넘겨줘.
        // MemberRepository 의 구현체를 생성하는 건 AppConfig 가 수행하고 있지.
        // 그 구현체를 MemberServiceImpl 에 넣어주고 있어.
        // 이거를 의존 객체를 외부에서 주입해주고 있다고 해서 '의존 관계 주입'이라고 말함. Dependency Injection, DI
    }


    // intelliJ 단축키 - Ctrl + E : Recent Files 최근에 열었던 파일들 목록을 보여줘서 작업 중이던 파일로 빠르게 돌아갈 수 있음. 바로 타자 치면 검색도 가능. 바로 엔터 치면 바로 이전 파일로 돌아감.

    public OrderService orderService() {

        return new OrderServiceImpl(new MemoryMemberRepository(), new FixDiscountPolicy());
    }

    // 이렇게 해서 DIP를 지키게 됨.
    // 이전에는 MemberServiceImpl이나 OrderServiceImpl이 과도한 책임을 수행하고 있었음.
    // 이제는 관심사를 분리하고 기능을 AppConfig가 가져오면서 그 문제가 해결됨.
    // MemberServiceImpl이나 OrderServiceImpl은 각각 MemberService의 역할과 OrderService의 역할을 '수행'하는 책임만 가지고 있음.
    // 단일 책임 원칙이 지켜지는 것.
}
