package hello.coreSpring;

import hello.coreSpring.member.Grade;
import hello.coreSpring.member.Member;
import hello.coreSpring.member.MemberService;
import hello.coreSpring.order.Order;
import hello.coreSpring.order.OrderService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class OrderApp {

    public static void main(String[] args) {
//        AppConfig appConfig = new AppConfig();
//        MemberService memberService = appConfig.memberService();
//        OrderService orderService = appConfig.orderService();

        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
        MemberService memberService = applicationContext.getBean("memberService", MemberService.class);
        OrderService orderService = applicationContext.getBean("orderService", OrderService.class);


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

    // 맨처음에는 OrderService 이나 DiscountPolicy 같은 애들 애들의 구현체를 직접 생성해서 가져다 썼지.
    // OrderService orderService = new OrderServiceImpl(); / DiscountPolicy discountPolicy = new FixDiscountPolicy();
    // 위와 같은 식으로.
    // 그런데 이러한 코드는 OCP 를 위반하고 DIP 를 위반하고 있었어.
    // 그래서 AppConfig 를 따로 만들고 구현체를 생성하는 기능은 모두 AppConfig 로 넘겨 버렸지.
    // 그리고 그 뒤로는 구현체를 직접 생성해서 쓰지 않고 모두 AppConfig 통해서 받아다가 썼어. 용돈 받아다 쓰는 것처럼.
    // AppConfig appConfig = new AppConfig(); OrderService orderService = appConfig.orderService();
    // 위와 같은 식으로.
    // 이렇게 AppConfig 를 만든 뒤로 OCP 와 DIP 를 지키면서 애플리케이션을 만들 수 있었지.
    // 여기서 한 번 더. Spring 을 적용하면서 우리는 더 이상 AppConfig 가 아니라 Spring 이 제공하는 IoC / DI 컨테이너를 통해서 받아와.
    // AppConfig 설정 정보를 스프링 쪽으로 넘겨 주고, 그러면 그 설정 정보대로 스프링이 구현체를 구성해서 관리해 주고, 나머지 애들은 모두 스프링 컨테이너한테 받아다 써.
    // 나머지 애들은 AppConfig 로부터 직접 구현체를 받아다 쓰는 게 아니라, 스프링 컨테이너한테 받아다 쓰는 거야.
    // 애들은 직접 구현체를 생성해서 쓰다가, 그 일을 AppConfig 에 넘겼다가, AppConfig 는 다시 스프링 컨테이너에게 넘겼어.
    // 이렇게 되면 더 불편하고 코드도 늘어나는 것 같은데, 어떤 장점들이 있을까?
    // 앞으로 강의에서 알아보자.


}
