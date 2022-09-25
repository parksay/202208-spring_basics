package hello.core.autowired;

import hello.coreAutoConfig.AutoAppConfig;
import hello.coreAutoConfig.discount.DiscountPolicy;
import hello.coreAutoConfig.member.Grade;
import hello.coreAutoConfig.member.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;
import java.util.Map;

public class AllBeanTest {

    @Test
    void findAllBean() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(AutoAppConfig.class, DiscountService.class);
        // 테스트 실행하려면 FixDiscountPolicy 에다가 @Component 붙여 줘야 함.
        // coreAutoConfig > discount > FixDiscountPolicy
        // 왜냐하면 RateDiscountPolicy 랑 겹치기 때문에 FixDiscountPolicy 쪽에는 @Component 어노테이션을 안 붙여 놨었음
        // @Component 가 RateDiscountPolicy 쪽에만 붙어 있으니 AutoAppConfig 에서 ComponentScan 할 때 FixDiscountPolicy 는 못 찾음.
        // RateDiscountPolicy 랑 FixDiscountPolicy 랑 둘 중에 당연히 RateDiscountPolicy 만 찾아서 빈으로 등록하고 관리해 줌.
        // 근데 우리 테스트에서는 둘 다 빈으로 등록되어 있어야 하므로 둘 다 @Component 어노테이션이 붙어 있어야 함.
        // 그럼 이렇게 되면 다시 문제가 되는 게 뭐냐 하면 스프링 컨테이너에 DiscountPolicy 타입으로 조회하면 빈을 두 개나 찾아 옴.
        // 왜냐하면 @Component 어노테이션이 FixDiscountPolicy 랑 RateDiscountPolicy 랑 둘 다 붙어 있으니까 둘 다 자동으로 스캔해 버림.
        // 그럼 또 이걸 해결하려면 RateDiscountPolicy 쪽에 @Primary 어노테이션을 붙여 줘야 함.
        // coreAutoConfig > discount > RateDiscountPolicy
        // 같은 타입으로 빈이 여러 개가 등록되어 있을 때 무엇을 우선적으로 쓸지 지정해 주는 어노테이션
        // 한 타입으로 조회한 여러 개 빈들 중에 @Primary 가 붙어 있는 빈이 있으면 그 친구부터 우선으로 잡아서 넣어줌.

        DiscountService discountService = ac.getBean(DiscountService.class);
        Member member = new Member(1L, "userA", Grade.VIP);
        int fixDiscountPrice = discountService.discount(member, 10000, "fixDiscountPolicy");

        Assertions.assertThat(discountService).isInstanceOf(DiscountService.class);
        Assertions.assertThat(fixDiscountPrice).isEqualTo(1000);

        int rateDiscountPrice = discountService.discount(member, 20000, "rateDiscountPolicy");
        Assertions.assertThat(rateDiscountPrice).isEqualTo(2000);
    }

    static class DiscountService {
        private final Map<String, DiscountPolicy> policyMap;
        private final List<DiscountPolicy> policies;

        @Autowired
        public DiscountService(Map<String, DiscountPolicy> policyMap, List<DiscountPolicy> policies) {
            this.policyMap = policyMap;
            this.policies = policies;
            System.out.println("policyMap = " + policyMap);
            System.out.println("policies = " + policies);
            // @Autowired 어노테이션을 붙여서 의존 관계를 자동으로 받아오려고 함.
            // 이때 Map 이나 List 타입으로 받으니까 그 클래스 타입으로 조회한 모든 빈을 불러다가 넣어 줌.
            // 그러면 일단 나는 그 클래스의 하위 타입 클래스들을 모두 들고 있는 것.
            // 그리고 필요할 때마다 동적으로 다르게 꺼내서 실행할 수 있음.
            // 예를 들어서 아래 discount() 메소드
        }

        public int discount(Member member, int price, String discountPolicyCode) {
            DiscountPolicy discountPolicy = this.policyMap.get(discountPolicyCode);
            return discountPolicy.discount(member, price);
            // 이 클래스는 현재 this.policyMap 랑 this.policies 필드를 들고 있음.
            // 이 필드들 안에는 DiscountPolicy 클래스 타입으로 조회한 모든 빈들이 들어 있음.
            // 왜냐하면 생성자 메소드에서 @Autowired 로 의존 관계를 자동 주입 받을 때 List 랑 Map 으로 받았기 때문에
            // 이 상태에서 여기서 discount() 메소드를 실행하면서 어떤 빈을 실행할지까지 파라미터 전달인자로 받을 수 있음
            // "fixDiscountPolicy" 또는 "rateDiscountPolicy" 라는 key 값을 파라미터로 받아서 그에 해당하는 빈을 꺼내 옴.
            // 이렇게 동적으로 꺼낸 빈으로 discount() 메소드를 실행해서 그 결과를 return 함.
            // 동적으로 빈을 꺼내서 사용하면서 다형성을 만족하는 코드를 짜는 방법 중 하나.
        }
    }
}
