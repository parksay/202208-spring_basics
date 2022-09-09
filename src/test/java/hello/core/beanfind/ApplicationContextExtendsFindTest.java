package hello.core.beanfind;

import hello.coreSpring.AppConfig;
import hello.coreSpring.discount.DiscountPolicy;
import hello.coreSpring.discount.FixDiscountPolicy;
import hello.coreSpring.discount.RateDiscountPolicy;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class ApplicationContextExtendsFindTest {

    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);
    
    // type 으로 조회하면 그 타입을 상속 받은 하위 객체들까지 모두 딸려나옴
    //           1
    //         /  \
    //        2    3
    //      /  \  /  \
    //     4   5 6    7
    // 이러면 클래스2 로 검색하면 4,5 딸려나오고
    // 클래스3 로 검색하면 6,7 딸려나오고
    // 1로 검색하면 모든 클래스 다 나오겠지
    
    
    @Test
    @DisplayName("부모 타입으로 조회 시에 자신이 둘 이상이면 중복 오류가 발생한다")
    void findBeanByParentTypeDuplicate() {
        // DiscountPolicy bean = ac.getBean(DiscountPolicy.class);
        // 이렇게 하면 DiscountPolicy 인터페이스로 두 개가 등록돼 있으니까 예외 던지겠지
        // 그걸 테스트로 만들면
        assertThrows(NoUniqueBeanDefinitionException.class,
                () -> ac.getBean(DiscountPolicy.class));

    }


    @Test
    @DisplayName("부모 타입으로 조회 시에 자신이 둘 이상이면 빈 이름을 지정하면 된다")
    void findBeanByParentTypeBeanName() {
        // DiscountPolicy bean = ac.getBean(DiscountPolicy.class);
        // 위에서 이렇게 하면 에러가 나는 걸 봤어
        // 그러면 이걸 어떻게 해결하나?
        // 뽑을 때 bean 이름을 같이 지정해 주면 됨.
        DiscountPolicy rateDiscountPolicy = ac.getBean("rateDiscountPolicy", DiscountPolicy.class);
        org.assertj.core.api.Assertions.assertThat(rateDiscountPolicy).isInstanceOf(RateDiscountPolicy.class);
    }

    @Test
    @DisplayName("특정 하위 타입으로 조회")
    void findBeanBySubType() {
        RateDiscountPolicy bean = ac.getBean("rateDiscountPolicy", RateDiscountPolicy.class);
        org.assertj.core.api.Assertions.assertThat(bean).isInstanceOf(RateDiscountPolicy.class);
        // 아니면 방법이 하나 더 있는데 내가 꺼내고 싶은 빈의 그 특정한 타입을 하위 타입을 지정하는 것
        // 물론 이런 코드가 좋지는 않음. 바뀌면 또 수정해야 하니까
    }

    @Test
    @DisplayName("부모 타입으로 모두 조회하기")
    void findAllBeanByTypeParentType() {
        Map<String, DiscountPolicy> beansOfType = ac.getBeansOfType(DiscountPolicy.class);
        org.assertj.core.api.Assertions.assertThat(beansOfType.size()).isEqualTo(2);
        for (String key : beansOfType.keySet()) {
            System.out.println("key = " + key + "  // value = " + beansOfType.get(key));
            // 원래는 이렇게 출력해가면서 테스트 하면 안 됨.
            // 테스트가 자동으로 돌아가게끔 해놨을 텐데 그걸 이렇게 일일이 출력해서 확인하도록 해두면 어느 세월에 함.
            // 그냥 테스트 안에서 뭐 보고 싶을 때 그럴 때는 할 수도 있겠지만
        }
    }

    @Test
    @DisplayName("부모 타입으로 모두 조회하기 - object")
    void findAllBeanByObjectType() {
        Map<String, Object> beansOfType = ac.getBeansOfType(Object.class);
        for (String key : beansOfType.keySet()) {
            System.out.println("key = " + key + "  // value = " + beansOfType.get(key));
        }
        // 모든 자바 객체가 가지고 있는 가장 상위 클래스는 Object
        // 따라서 Object 로 검색하니까 모든 빈이 다 딸려 나오겠지
        // 스프링이 내부에서 쓰려고 만들어 둔 빈까지 모두 출력됨.
    }


    // 우리가 짠 application 에서 봐도, ~~ServiceImpl이나 ~~App 에서 이렇게 직접 빈을 꺼내는 코드는 없었지
    // 사실 코드 짤 때 직접 getBean 해가지고 빈 받아서 쓸 일은 거의 없음.
    // 그럼에도 이렇게 테스트 코드까지 짜서 해본 이유는 스프링 컨테이너 내부가 어떻게 돌아가는지 감을 잡기 위해서.



    @Configuration
    static class TestConfig{
        @Bean
        public DiscountPolicy rateDiscountPolicy(){
            return new RateDiscountPolicy();
        }

        @Bean
        public DiscountPolicy fixDiscountPolicy(){
            return new FixDiscountPolicy();
        }
    }


    // 스프링 컨테이너 상속 관계
    // 가장 상위에는 BeanFactory 가 있음.
    // 방금 우리가 사용한 거의 모든 기술 getBean 이나 getBeansOfType 이런 것들 다 BeanFactory 에서 제공하는 기능
    // 그럼 처음부터 BeanFactory 가져다 쓰면 될 걸 왜 AnnotationConfigApplicationContext 같은 걸 쓰냐?
    // ApplicationContext 는 BeanFactory 도 상속 받았지만, 그 외에 여러 가지 다른 인터페스도 상속 받고 있음
    // BeanFactory 뿐만 아니라 스프링에서 제공하는 편리한 기능등을 모아서 ApplicationContext 에 모아둔 것.
    // 어떤 개발을 할 때든 비슷하게 필요한 기능들을 기본적으로 가지고 있음.
    // 실제로 ApplicationContext 선언하는 거 보면 엄청 다양한 걸 상속받고 있음.
    // public interface ApplicationContext extends EnvironmentCapable, ListableBeanFactory, HierarchicalBeanFactory,
    //		MessageSource, ApplicationEventPublisher, ResourcePatternResolver
    // 다국어 설정할 때 유용한 spring Message, 테스트서버/운영서버 이런 거 구분해주는 기능, 등등.

}
