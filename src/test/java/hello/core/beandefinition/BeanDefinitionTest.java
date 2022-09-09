package hello.core.beandefinition;

import hello.coreSpring.AppConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class BeanDefinitionTest {

    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

    // BeanDefinition 자체는 빈의 메타 정보를 들고 있는 인터페이스임.
    // 앞에서 말했든 스프링 설정은 어노테이션이든 xml 이든 자유롭게 사용할 수 있음.
    // 어떤 방식을 사용하든 BeanDefinition 만 만들어서 등록만 하면 됨.
    // Bean 하나당 BeanDefinition 하나가 생김.
    // 역할과 구현을 잘 나누어서 설계한 것이지. 어노테이션 방식이든, xml 방식이든, 받아다가 사용하는 입장에서는 어떤 방식으로 만들었는지 몰라도 됨.

    @Test
    @DisplayName("빈 설정 메타 정보 확인")
    void findApplicationBean() {
        // 등록돼 있는 definition 들의 이름 쭈우욱 받아 오기
        String[] beanDefinitionNames = ac.getBeanDefinitionNames();
        // 이름들 목록 하나씩 쭉 보면서
        for (String beanDefinitionName : beanDefinitionNames) {
            // 이름으로 해당 빈을 하나씩 꺼내기
            BeanDefinition beanDefinition = ac.getBeanDefinition(beanDefinitionName);

            // 만약에 그 종류가 application 이라면 출력
            if(beanDefinition.getRole() == BeanDefinition.ROLE_APPLICATION) {
                System.out.println("beanDefinitionName = " + beanDefinitionName + " // beanDefinition = " + beanDefinition);
            }
        }
        // 출력해 보면 많은 메타 정보가 드러 있음. lazyInit / abstract / scope / factoryBeanName / factoryMethodName / .....
    }
}
