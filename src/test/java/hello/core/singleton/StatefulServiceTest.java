package hello.core.singleton;

import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import static org.junit.jupiter.api.Assertions.*;

class StatefulServiceTest {
    @Test
    void statefulServiceSingleton() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);


        StatefulService statefulService1 = ac.getBean(StatefulService.class);
        StatefulService statefulService2 = ac.getBean(StatefulService.class);

        // 사용자 A 가 10000원짜리 상품 주문
        statefulService1.order("userA", 100000);
        // 사용자 A 주문 처리하던 사이에 사용자 B 가 20000원짜리 상품 주문
        statefulService2.order("userB", 200000);

        // 다시 사용자 A 가 주문했던 금액 조회
        int price = statefulService1.getPrice();
        System.out.println("price = " + price);
        // 사용자 A 의 주문을 처리하던 사람은 10000원을 기대하고 있었을 텐데, 20000원이 나왔음.
        // 그러면 사용자 A는 화면에는 구매 금액이 10000원이라고 안내가 나오는데, 실제로 뒤에서 결제된 금액은 20000원 되고 그러는 거지;
        
        // 이래서 싱글톤 디자인 class는 statefule 하게 설계하면 안 됨.
    }


    static class TestConfig {
        @Bean
        public StatefulService statefulService (){
            return new StatefulService();
        }
    }
}